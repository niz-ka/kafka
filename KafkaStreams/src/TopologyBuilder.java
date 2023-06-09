import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import schema.AggregationSchema;
import schema.AnomalySchema;
import serde.CustomSerdes;
import time.EventTimeExtractor;
import time.TableTimeExtractor;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class TopologyBuilder {
    private final KStream<String, BicycleResultRecord> bicycleResultRecords;
    private final KTable<Long, StationsListRecord> stationsListRecords;

    public TopologyBuilder(StreamsBuilder builder, String inputBicycleTopic, String inputStationsTopic) {
        bicycleResultRecords = builder.stream(
                inputBicycleTopic,
                Consumed.with(Serdes.String(), Serdes.String())
                        .withTimestampExtractor(new EventTimeExtractor())
        ).mapValues(BicycleResultRecord::fromString);

        stationsListRecords = builder.table(
                inputStationsTopic,
                Consumed.with(Serdes.Long(), Serdes.String())
                        .withTimestampExtractor(new TableTimeExtractor())
        ).mapValues(StationsListRecord::fromString);
    }

    public TopologyBuilder buildAggregationProcessing(String delayMode, String outputTopic) {
        KTable<Windowed<DayStationIdKey>, BicycleAggregator> windowedBicycleResults = bicycleResultRecords
                .groupBy(
                        (key, value) -> new DayStationIdKey(new SimpleDateFormat("yyyy-MM-dd").format(value.getEventTime()), value.getStationId()),
                        Grouped.with(CustomSerdes.DayStationIdKey(), CustomSerdes.BicycleResultRecord())
                )
                .windowedBy(TimeWindows.ofSizeAndGrace(Duration.ofHours(24), Duration.ofHours(1)))
                .aggregate(
                        () -> new BicycleAggregator(0L, 0L, 0.0),
                        (key, value, aggregator) -> {
                            if (value.getStartStop() == BicycleEventType.START) {
                                aggregator.setStartCount(aggregator.getStartCount() + 1);
                            } else {
                                aggregator.setStopCount(aggregator.getStopCount() + 1);
                            }
                            aggregator.setTemperatureSum(aggregator.getTemperatureSum() + value.getTemperature());
                            return aggregator;
                        },
                        Materialized.with(CustomSerdes.DayStationIdKey(), CustomSerdes.BicycleAggregator())
                );

        if (delayMode.equals("C")) {
            windowedBicycleResults = windowedBicycleResults
                    .suppress(Suppressed.untilWindowCloses(Suppressed.BufferConfig.unbounded()));
        }

        windowedBicycleResults
                .toStream()
                .map(
                        (key, value) -> {
                            BicycleOutputRecord bicycleOutputRecord = new BicycleOutputRecord(
                                    key.key().getDay(),
                                    key.key().getStationId(),
                                    value.getStartCount(),
                                    value.getStopCount(),
                                    value.getTemperatureSum() / (value.getStartCount() + value.getStopCount()));
                            return KeyValue.pair(key.key(), bicycleOutputRecord);
                        }
                )
                .selectKey((key, value) -> value.getStationId())
                .join(
                        stationsListRecords,
                        (bicycleOutputRecord, stationsListRecord) -> new BicycleFinalRecord(
                                bicycleOutputRecord.getDay(),
                                stationsListRecord.getStationName(),
                                bicycleOutputRecord.getStartCount(),
                                bicycleOutputRecord.getStopCount(),
                                bicycleOutputRecord.getTemperatureAverage()
                        ),
                        Joined.with(Serdes.Long(), CustomSerdes.BicycleOutputRecord(), CustomSerdes.StationsListRecord())
                )
                .mapValues((key, value) -> {
                    try {
                        String payload = new ObjectMapper().writer().writeValueAsString(value);
                        return AggregationSchema.construct(payload);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .to(outputTopic, Produced.with(Serdes.Long(), Serdes.String()));

        return this;
    }

    public TopologyBuilder buildAnomalyProcessing(String outputTopic, long D, double P) {
        bicycleResultRecords
                .groupBy((key, value) -> value.getStationId(), Grouped.with(Serdes.Long(), CustomSerdes.BicycleResultRecord()))
                .windowedBy(TimeWindows.ofSizeAndGrace(Duration.ofMinutes(D), Duration.ofHours(1)))
                .aggregate(
                        () -> 0L,
                        (key, value, aggregator) -> value.getStartStop() == BicycleEventType.START ? aggregator - 1 : aggregator + 1,
                        Materialized.with(Serdes.Long(), Serdes.Long())
                )
                .suppress(Suppressed.untilTimeLimit(Duration.ofMinutes(10), Suppressed.BufferConfig.unbounded()))
                .toStream()
                .map(
                        (key, value) -> {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String windowStart = formatter.format(new Date(key.window().start()));
                            String windowEnd = formatter.format(new Date(key.window().end()));
                            return KeyValue.pair(key.key(), new AnomalyRecord(windowStart, windowEnd, value));
                        }
                )
                .join(
                        stationsListRecords,
                        (anomalyRecord, stationListRecord) ->
                                new FinalAnomalyRecord(
                                        anomalyRecord.getWindowStart(),
                                        anomalyRecord.getWindowEnd(),
                                        stationListRecord.getStationName(),
                                        anomalyRecord.getNumberN() > 0L ? anomalyRecord.getNumberN() : 0L,
                                        anomalyRecord.getNumberN() < 0L ? Math.abs(anomalyRecord.getNumberN()) : 0L,
                                        stationListRecord.getDocksInService(),
                                        Math.abs(anomalyRecord.getNumberN()) / (double) stationListRecord.getDocksInService()
                                ),
                        Joined.with(Serdes.Long(), CustomSerdes.AnomalyRecord(), CustomSerdes.StationsListRecord())
                )
                .filter((key, value) -> value.getRatioN() * 100 >= P)
                .mapValues((key, value) -> {
                    try {
                        String payload = new ObjectMapper().writer().writeValueAsString(value);
                        return AnomalySchema.construct(payload);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .to(outputTopic, Produced.with(Serdes.Long(), Serdes.String()));

        return this;
    }
}

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;
import time.EventTimeExtractor;

import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(final String[] args) {
        if (args.length < 9) {
            System.out.println("Required params: " +
                    "applicationId " +
                    "bootstrapServers " +
                    "inputBicycleResult " +
                    "inputStationsList " +
                    "outputAggregation " +
                    "outputAnomaly " +
                    "delay " +
                    "D " +
                    "P");
            System.exit(-1);
        }

        String applicationId = args[0];
        String bootstrapServers = args[1];
        String inputBicycleTopic = args[2];
        String inputStationsTopic = args[3];
        String outputAggregation = args[4];
        String outputAnomaly = args[5];
        String delay = args[6];
        long D = Long.parseLong(args[7]);
        double P = Double.parseDouble(args[8]);

        if (!Objects.equals(delay, "A") && !Objects.equals(delay, "C")) {
            throw new IllegalArgumentException("Delay must be A or C!");
        }

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, EventTimeExtractor.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        StreamsBuilder builder = new StreamsBuilder();

        TopologyBuilder topologyBuilder = new TopologyBuilder(builder, inputBicycleTopic, inputStationsTopic);
        topologyBuilder
                .buildAggregationProcessing(delay, outputAggregation)
                .buildAnomalyProcessing(outputAnomaly, D, P);

        KafkaStreams streams = new KafkaStreams(builder.build(), props);

        final CountDownLatch latch = new CountDownLatch(1);

        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });

        try {
            streams.setUncaughtExceptionHandler(exception -> {
                System.out.println(exception.getMessage());
                exception.printStackTrace();
                return StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse.SHUTDOWN_APPLICATION;
            });
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }

        System.exit(0);
    }
}

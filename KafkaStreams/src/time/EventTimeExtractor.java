package time;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class EventTimeExtractor implements TimestampExtractor {
    @Override
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
        String line = (String) record.value();
        String[] values = line.split(",");
        return Date.from(Instant.from(DateTimeFormatter.ISO_INSTANT.parse(values[2]))).getTime();
    }
}
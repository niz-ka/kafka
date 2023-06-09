package time;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

// FIXME: Consider using GlobalKTable
// see: https://stackoverflow.com/questions/56556270/can-kafka-streams-be-configured-to-wait-for-ktable-to-load
public class TableTimeExtractor implements TimestampExtractor {
    @Override
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
        return 0L;
    }
}
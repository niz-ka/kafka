import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

public class CsvProducer {
    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.out.println("Należy podać trzy parametry: " +
                    "inputCsv outputTopic bootstrapServers");
            System.exit(-1);
        }

        String inputCsv = args[0];
        String outputTopic = args[1];
        String bootstrapServers = args[2];

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.LongSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<Long, String> producer = new KafkaProducer<>(props);
        Path path = Paths.get(inputCsv);
        try (Stream<String> lines = Files.lines(path)) {
            lines.skip(1)
                    .forEach(line -> {
                            Long key = Long.parseLong(line.split(",")[0]);
                            producer.send(new ProducerRecord<>(outputTopic, key, line));
                    });
        }
        producer.close();
    }
}

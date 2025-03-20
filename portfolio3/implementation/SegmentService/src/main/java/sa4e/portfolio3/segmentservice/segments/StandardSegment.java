package sa4e.portfolio3.segmentservice.segments;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import sa4e.portfolio3.common.Segment;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.Random;

public class StandardSegment implements SegmentRoutine {
    private KafkaConsumer<String, String> consumer;
    private KafkaProducer<String, String> producer;
    private Segment segmentData;
    private Random rand = new Random();

    public static SegmentRoutine create(Segment segment) {
        StandardSegment s = new StandardSegment();

        s.segmentData = segment;
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // Lower latency configurations
        props.put("linger.ms", "0");
        props.put("batch.size", "16384");
        props.put("acks", "all");
        props.put("retries", Integer.MAX_VALUE);


        s.producer = new KafkaProducer<>(props);
        s.consumer = new KafkaConsumer<>(props);
        s.consumer.subscribe(Collections.singletonList(segment.getSegmentId()));

        return s;
    }

    @Override
    public void mainLoop() throws RuntimeException {
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    String chariotJson = record.value();
                    String nextSegment = segmentData.getNextSegments().get(
                            rand.nextInt(segmentData.getNextSegments().size())
                    );

                    ProducerRecord<String, String> producerRecord = new ProducerRecord<>(
                            nextSegment,
                            record.key(),
                            chariotJson
                    );
                    producer.send(producerRecord).get();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        consumer.close();
        producer.close();
    }
}

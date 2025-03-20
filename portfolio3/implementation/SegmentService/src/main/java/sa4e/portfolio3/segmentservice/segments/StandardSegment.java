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
import java.util.concurrent.ExecutionException;

public class StandardSegment implements SegmentRoutine {
    protected KafkaConsumer<String, String> consumer;
    protected KafkaProducer<String, String> producer;
    protected Segment segmentData;
    protected Random rand = new Random();

    @Override
    public void mainLoop() throws RuntimeException {
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    handleRecord(record);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void handleRecord(ConsumerRecord<String, String> record) throws ExecutionException, InterruptedException {
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

    @Override
    public void init(Segment segment) {
        segmentData = segment;
        Properties producerProps = new Properties();
        producerProps.put("bootstrap.servers", "localhost:9092");
        producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // Lower latency configurations
        producerProps.put("linger.ms", "0");
        producerProps.put("batch.size", "16384");
        producerProps.put("acks", "all");
        producerProps.put("retries", Integer.MAX_VALUE);

        Properties consumerProps = new Properties();
        consumerProps.put("bootstrap.servers", "localhost:9092");
        consumerProps.put("group.id", segmentData.getSegmentId());
        consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        producer = new KafkaProducer<>(producerProps);
        consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Collections.singletonList(segment.getSegmentId()));
    }

    @Override
    public void close() {
        consumer.close();
        producer.close();
    }
}

package sa4e.portfolio3.segmentservice.segments;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import sa4e.portfolio3.common.KafkaConfig;
import sa4e.portfolio3.common.Segment;

import java.time.Duration;
import java.util.Collections;
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
        sendToNextSegment(chariotJson);
        System.out.println(segmentData.getSegmentId() + ": Chariot passed: " + chariotJson);
    }

    protected void sendToNextSegment(String chariotJson) throws ExecutionException, InterruptedException {
        String nextSegment = segmentData.getNextSegments().get(
                rand.nextInt(segmentData.getNextSegments().size())
        );
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(
                nextSegment,
                chariotJson,
                chariotJson
        );
        producer.send(producerRecord).get();
    }

    @Override
    public void init(Segment segment) {
        segmentData = segment;

        producer = new KafkaProducer<>(KafkaConfig.getProducerProps());
        consumer = new KafkaConsumer<>(KafkaConfig.getConsumerProps(segmentData.getSegmentId()));
        consumer.subscribe(Collections.singletonList(segment.getSegmentId()));
    }

    @Override
    public void close() {
        consumer.close();
        producer.close();
    }
}

package sa4e.portfolio3.cucuco;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import sa4e.portfolio3.common.KafkaConfig;

public class KafkaUtil {
    public static void createTopics(List<String> topics) {
        List<NewTopic> newTopics = topics.stream()
            .map(topic -> new NewTopic(topic, 4, (short) 2))
            .toList();
        try (AdminClient admin = AdminClient.create(KafkaConfig.getAdminProps())) {
            admin.createTopics(newTopics).all().get();
            System.out.println("Topics created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteTopics(List<String> topics) {
        try (AdminClient admin = AdminClient.create(KafkaConfig.getAdminProps())) {
            admin.deleteTopics(topics).all().get();
            System.out.println("Topics deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void send(String topic, String key, String value) {
        KafkaProducer<String, String> producer = new KafkaProducer<>(KafkaConfig.getProducerProps());
        try {
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
            Future<RecordMetadata> future = producer.send(record);
            RecordMetadata metadata = future.get();  // Synchronously wait for send to complete
            System.out.printf("Sent record to topic %s partition %d with offset %d%n",
                    metadata.topic(), metadata.partition(), metadata.offset());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}

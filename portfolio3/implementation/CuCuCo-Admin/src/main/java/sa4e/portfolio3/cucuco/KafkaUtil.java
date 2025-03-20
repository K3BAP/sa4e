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

public class KafkaUtil {
    public static void createTopics(List<String> topics) {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        List<NewTopic> newTopics = topics.stream()
            .map(topic -> new NewTopic(topic, 1, (short) 1))
            .toList();
        try (AdminClient admin = AdminClient.create(props)) {
            admin.createTopics(newTopics).all().get();
            System.out.println("Topics created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteTopics(List<String> topics) {
        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        try (AdminClient admin = AdminClient.create(config)) {
            admin.deleteTopics(topics).all().get();
            System.out.println("Topics deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void send(String topic, String key, String value) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
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

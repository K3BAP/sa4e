package sa4e.portfolio3.cucuco;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;

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
}

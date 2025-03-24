package sa4e.portfolio3.common;

import java.util.Properties;

public class KafkaConfig {
    public static Properties getProducerProps() {
        Properties producerProps = new Properties();
        producerProps.put("bootstrap.servers", "localhost:9092");
        producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // Lower latency configurations
        producerProps.put("linger.ms", "0");
        producerProps.put("batch.size", "16384");
        producerProps.put("acks", "all");
        producerProps.put("retries", Integer.MAX_VALUE);

        return producerProps;
    }

    public static Properties getConsumerProps(String groupId) {
        Properties consumerProps = new Properties();
        consumerProps.put("bootstrap.servers", "localhost:9092");
        consumerProps.put("group.id", groupId);
        consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("auto.offset.reset", "earliest");

        return consumerProps;
    }

    public static Properties getAdminProps() {
        Properties adminProps = new Properties();
        adminProps.put("bootstrap.servers", "localhost:9092");

        return adminProps;
    }
}

package ymyoo.infra.messaging.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by 유영모 on 2016-12-21.
 */
public class ExactlyOnceStaticConsumer {
    private static OffsetManager offsetManager = new OffsetManager("storage1");
    public static void main(String[] str) throws Exception {
        System.out.println("Starting ExactlyOnceStaticConsumer ...");
        readMessages();
    }
    private static void readMessages() throws Exception {
        KafkaConsumer<String, String> consumer = createConsumer();
        String topic = "normal-topic";
        int partition = 1;
        TopicPartition topicPartition =
                registerConsumerToSpecificPartition(consumer, topic, partition);
        // Read the offset for the topic and partition from external storage.
        long offset = offsetManager.readOffsetFromExternalStore(topic, partition);
        // Use seek and go to exact offset for that topic and partition.
        consumer.seek(topicPartition, offset);
        processRecords(consumer);
    }
    private static KafkaConsumer<String, String> createConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        String consumeGroup = "cg2";
        props.put("group.id", consumeGroup);
        // Below is a key setting to turn off the auto commit.
        props.put("enable.auto.commit", "false");
        props.put("heartbeat.interval.ms", "2000");
        props.put("session.timeout.ms", "6001");
        // control maximum data on each poll, make sure this value is bigger than the maximum                 // single message size
        props.put("max.partition.fetch.bytes", "40");
        props.put("key.deserializer",                                     "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",                                     "org.apache.kafka.common.serialization.StringDeserializer");
        return new KafkaConsumer<String, String>(props);
    }
    /**
     * Manually listens for specific topic partition. But, if you are looking for example of how to                * dynamically listens to partition and want to manually control offset then see
     * ExactlyOnceDynamicConsumer.java
     */
    private static TopicPartition registerConsumerToSpecificPartition(
            KafkaConsumer<String, String> consumer, String topic, int partition) {
        TopicPartition topicPartition = new TopicPartition(topic, partition);
        List<TopicPartition> partitions = Arrays.asList(topicPartition);
        consumer.assign(partitions);
        return topicPartition;
    }
    /**
     * Process data and store offset in external store. Best practice is to do these operations
     * atomically.
     */
    private static void processRecords(KafkaConsumer<String, String> consumer) throws Exception {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(),                                                 record.key(), record.value());
                offsetManager.saveOffsetInExternalStore(record.topic(), record.partition(),                                                 record.offset());
            }
        }
    }
}

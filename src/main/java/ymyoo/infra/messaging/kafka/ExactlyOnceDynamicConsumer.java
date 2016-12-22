package ymyoo.infra.messaging.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by 유영모 on 2016-12-21.
 */
public class ExactlyOnceDynamicConsumer {
    private static OffsetManager offsetManager = new OffsetManager("storage2");
    public static void main(String[] str) throws InterruptedException {
        System.out.println("Starting ExactlyOnceDynamicConsumer ...");
        readMessages();
    }
    private static void readMessages() throws InterruptedException {
        KafkaConsumer<String, String> consumer = createConsumer();
        // Manually controlling offset but register consumer to topics to get dynamically
        //  assigned partitions. Inside MyConsumerRebalancerListener use
        // consumer.seek(topicPartition,offset) to control offset which messages to be read.
        consumer.subscribe(Arrays.asList("normal-topic"),
                new MyConsumerRebalancerListener(consumer));
        processRecords(consumer);
    }
    private static KafkaConsumer<String, String> createConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        String consumeGroup = "cg3";
        props.put("group.id", consumeGroup);
        // Below is a key setting to turn off the auto commit.
        props.put("enable.auto.commit", "false");
        props.put("heartbeat.interval.ms", "2000");
        props.put("session.timeout.ms", "6001");
        // Control maximum data on each poll, make sure this value is bigger than the maximum                   // single message size
        props.put("max.partition.fetch.bytes", "40");
        props.put("key.deserializer",                                 "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",                         "org.apache.kafka.common.serialization.StringDeserializer");
        return new KafkaConsumer<String, String>(props);
    }
    private static void processRecords(KafkaConsumer<String, String> consumer) {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
                // Save processed offset in external storage.
                offsetManager.saveOffsetInExternalStore(record.topic(), record.partition(), record.offset());
            }
        }
    }
}

package ymyoo.infra.messaging.remote.channel;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

/**
 * Created by 유영모 on 2016-12-06.
 */
public class MessageConsumer {
    private String channel;
    private KafkaConsumer<String, String> consumer;

    public MessageConsumer(String channel) {
        this.channel = channel;

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        this.consumer = new KafkaConsumer<>(props);
    }

    public String receive(String messageId) {
        consumer.subscribe(Arrays.asList(channel));

//        try {
//            while(true) {
//                ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
//                for (TopicPartition partition : records.partitions()) {
//                    List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
//                    for (ConsumerRecord<String, String> record : partitionRecords) {
//                        System.out.println(record.offset() + ": " + record.value());
//                    }
//                    long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
//                    consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));
//                }
//            }
//        } finally {
//            consumer.close();
//        }


        return "{\"test\":\"test\"}";
    }
}

package ymyoo.infra.messaging.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * Created by 유영모 on 2016-12-21.
 */
public class AtLeastOnceConsumer {
    public static void main(String[] str) throws InterruptedException {
        System.out.println("Starting AutoOffsetGuranteedAtLeastOnceConsumer ...");
        execute();
    }


    private static void execute() throws InterruptedException {
        KafkaConsumer<String, String> consumer = createConsumer();
        // Subscribe to all partition in that topic. 'assign' could be used here
        // instead of 'subscribe' to subscribe to specific partition.
        consumer.subscribe(Arrays.asList("normal-topic"));
        processRecords(consumer);
    }
    private static KafkaConsumer<String, String> createConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        String consumeGroup = "cg1";
        props.put("group.id", consumeGroup);
        // Set this property, if auto commit should happen.
        props.put("enable.auto.commit", "false");
        // Make Auto commit interval to a big number so that auto commit does not happen,
        // we are going to control the offset commit via consumer.commitSync(); after processing             // message.
        props.put("auto.commit.interval.ms", String.valueOf(Integer.MAX_VALUE));
        // This is how to control number of messages being read in each poll
        props.put("max.partition.fetch.bytes", "35");
        props.put("heartbeat.interval.ms", "3000");
        props.put("session.timeout.ms", "6001");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        return new KafkaConsumer<String, String>(props);
    }
    private static void processRecords(KafkaConsumer<String, String> consumer) throws InterruptedException {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);

//
//            for (ConsumerRecord<String, String> record : records) {
//                System.out.printf("\n\roffset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
//
//
//                if(record.value().equals("3")) {
//                    System.out.println("Three ... !!!!!!!!!");
//
//                    long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
//                    consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));
//
//                    Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
//                    offsets.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset()));
//                    offsets.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1));
//                    consumer.commitSync(offsets);
//                }
//            }

            for (TopicPartition partition : records.partitions()) {
                List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
                for (ConsumerRecord<String, String> record : partitionRecords) {
                    System.out.println(record.offset() + ": " + record.value());
                    if (record.value().equals("3")) {
                        System.out.println("Three ... !!!!!!!!!");
                        long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
                        consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));

                    }
                }

            }
            process();
            // Below call is important to control the offset commit. Do this call after you
            // finish processing the business process.
        }
    }

    private static void process() throws InterruptedException {
        // create some delay to simulate processing of the record.
        Thread.sleep(20);
    }



}

package ymyoo.infra.messaging.remote.channel;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by 유영모 on 2016-11-16.
 */
public class MessageBroker implements Runnable {
    private List<String> channels;
    private KafkaConsumer<String, String> consumer;

    public MessageBroker(String... channels) {
        this.channels = Arrays.asList(channels);

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        this.consumer = new KafkaConsumer<>(props);
    }

    @Override
    public void run() {
        consumer.subscribe(channels);
        try {
            while (true) {
                // 메시지 수신
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    // 메시지 라우팅
                    MessageRouter.route(record.topic(), record.key(), record.value());
                }
            }
        } finally {
            consumer.close();
        }

    }
}

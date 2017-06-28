package ymyoo.infra.messaging;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by 유영모 on 2016-12-29.
 */
public class EventDrivenMessageConsumer {
    private String channel;
    private KafkaConsumer<String, String> consumer;

    public EventDrivenMessageConsumer(String channel) {
        this.channel = channel;
        initKafka();
    }

    private void initKafka() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("interceptor.classes", "ymyoo.infra.messaging.core.interceptor.MessageConsumerInterceptorImpl");

        this.consumer = new KafkaConsumer<>(props);
        this.consumer.subscribe(Arrays.asList(channel));
    }

    public List<Message> poll() {
        ConsumerRecords<String, String> records = consumer.poll(100);
        return StreamSupport.stream(records.spliterator(), false)
                .map(record -> new Gson().fromJson(record.value(), Message.class)).collect(Collectors.toList());
    }

    public void close() {
        consumer.close();
    }
}

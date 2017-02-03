package ymyoo.messaging.core;

import com.google.gson.Gson;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * 메시지 생산자
 *
 * Created by 유영모 on 2016-12-05.
 */
public class MessageProducer {
    private Producer<String, String> producer;

    public MessageProducer() {
        initKafkaProducer();
    }

    private void initKafkaProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("interceptor.classes", "ymyoo.messaging.core.interceptor.MessageProducerInterceptorImpl");

        this.producer = new KafkaProducer<>(props);
    }

    public void send(final String channel, final Message message) {
        producer.send(new ProducerRecord<>(channel, message.getMessageId(), new Gson().toJson(message)));
        producer.close();
    }
}

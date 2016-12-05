package ymyoo.infra.messaging.remote.channel;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by 유영모 on 2016-12-05.
 */
public class MessageProducer {
    private String requestChannel;
    private String replyChannel;

    Producer<String, String> producer;

    public MessageProducer(String requestChannel, String replyChannel) {
        this.requestChannel = requestChannel;
        this.replyChannel = replyChannel;

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        this.producer = new KafkaProducer<>(props);
    }

    public MessageProducer(String requestChannel, String replyChannel, Producer<String, String> producer) {
        this.requestChannel = requestChannel;
        this.replyChannel = replyChannel;
        this.producer = producer;
    }

    public void send(final String messageId, final String messageBody) {
        String messageKey = String.join("::",  Arrays.asList(messageId, replyChannel));
        producer.send(new ProducerRecord<String, String>(requestChannel, messageKey, messageBody));
        producer.close();
    }
}

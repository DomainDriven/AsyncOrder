package ymyoo.infra.messaging.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by 유영모 on 2016-12-20.
 */
public class ProducerExample {

    public static void main(String[] str) throws InterruptedException, IOException {
        System.out.println("Starting ProducerExample ...");
        sendMessages();
    }

    private static void sendMessages() throws InterruptedException, IOException {
        Producer<String, String> producer = createProducer();
        sendMessages(producer);
        // Allow the producer to complete sending of the messages before program exit.
        Thread.sleep(20);
    }

    private static Producer<String, String> createProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        // Controls how much bytes sender would wait to batch up before publishing to Kafka.
        props.put("batch.size", 10);
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer(props);
    }

    private static void sendMessages(Producer<String, String> producer) {
        String topic = "normal-topic";
        int partition = 0;
        long record = 1;
        for (int i = 1; i <= 10; i++) {
            producer.send(
                    new ProducerRecord<String, String>(topic, partition, Long.toString(record),Long.toString(record++)));
        }
    }
}

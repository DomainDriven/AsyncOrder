package ymyoo.messaging;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Assert;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by 유영모 on 2017-01-02.
 */
public class KafkaIntegrationTest {
    public static boolean messageReceivedFlag = false;

    protected void sendMessage(String channel, Message message) {
        Properties producerProps = new Properties();
        producerProps.put("bootstrap.servers", "localhost:9092");
        producerProps.put("acks", "all");
        producerProps.put("retries", 0);
        producerProps.put("batch.size", 16384);
        producerProps.put("linger.ms", 1);
        producerProps.put("buffer.memory", 33554432);
        producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(producerProps);

        producer.send(new ProducerRecord<>(channel, message.getMessageId(), new Gson().toJson(message)));
        producer.close();
    }

    protected void waitCurrentThread(int seconds) {
        Object obj = new Object();
        synchronized (obj) {
            try {
                obj.wait(TimeUnit.SECONDS.toMillis(seconds));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    protected void assertSendingMessage(String channel, String id, MessageAssert messageAssert) {
        new Thread(() -> {
            Properties props = new Properties();
            props.put("bootstrap.servers", "localhost:9092");
            props.put("group.id", "test");
            props.put("enable.auto.commit", "true");
            props.put("auto.commit.interval.ms", "1000");
            props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

            consumer.subscribe(Arrays.asList(channel));
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    ConsumerRecords<String, String> records = consumer.poll(100);
                    for (ConsumerRecord<String, String> record : records) {
                        if(record.key().equals(id)) {
                            Message message = new Gson().fromJson(record.value(), Message.class);
                            messageAssert.assertMessage(message);
                            KafkaIntegrationTest.messageReceivedFlag = true;
                        }
                    }
                }
            } finally {
                consumer.close();
            }
        }).start();

        // 비동기 대기
        waitCurrentThread(5);
        Assert.assertTrue(KafkaIntegrationTest.messageReceivedFlag);
    }

    protected String generateId() {
        return java.util.UUID.randomUUID().toString().toUpperCase();
    }
}

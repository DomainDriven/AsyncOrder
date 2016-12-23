package ymyoo.order.messaging.endpoint.request;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by 유영모 on 2016-12-23.
 */
public class RequesterTest {

    @Test
    public void send() throws InterruptedException {
        // given
        String correlationId =  java.util.UUID.randomUUID().toString().toUpperCase();
        String requestChannel = "TEST-REQUEST";
        String replyChannel = "TEST-REPLY";

        Map<String, String> message = new HashMap<>();
        message.put("productId", "prd-1234");
        message.put("orderQty", "2");

        // when
        Requester requester = new Requester();
        requester.send(requestChannel, replyChannel, correlationId, new Gson().toJson(message));

        // then
        new Thread(() -> {
            Properties props = new Properties();
            props.put("bootstrap.servers", "localhost:9092");
            props.put("group.id", "test");
            props.put("enable.auto.commit", "true");
            props.put("auto.commit.interval.ms", "1000");
            props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

            consumer.subscribe(Arrays.asList(requestChannel));
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    ConsumerRecords<String, String> records = consumer.poll(100);
                    for (ConsumerRecord<String, String> record : records) {
                        Assert.assertEquals(correlationId + "::" + replyChannel, record.key());
                        Assert.assertEquals(new Gson().toJson(message), record.value());
                    }
                }
            } finally {
                consumer.close();
            }
        }).start();

        // 비동기 대기
        Object obj = new Object();
        synchronized (obj) {
            obj.wait(TimeUnit.SECONDS.toMillis(5));
        }
    }

    @Test
    public void receive() {

    }
}
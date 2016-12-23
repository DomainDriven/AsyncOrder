package ymyoo.order.messaging.endpoint.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ymyoo.infra.messaging.remote.channel.MessageConsumer;
import ymyoo.infra.messaging.remote.channel.MessageProducer;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by 유영모 on 2016-12-23.
 */
public class RequesterTest {
    static boolean messageReceivedFlag = false;

    final String TEST_REQUEST_CHANNEL = "TEST-REQUEST";
    final String TEST_REPLY_CHANNEL = "TEST-REPLY";

    @Before
    public void setUp() throws Exception {
        RequesterTest.messageReceivedFlag = false;
    }

    @Test
    public void send() throws InterruptedException {
        // given
        String correlationId =  java.util.UUID.randomUUID().toString().toUpperCase();

        Map<String, String> message = new HashMap<>();
        message.put("productId", "prd-1234");
        message.put("orderQty", "2");

        // when
        Requester requester = new Requester(TEST_REQUEST_CHANNEL, TEST_REPLY_CHANNEL, correlationId);
        requester.send(new Gson().toJson(message));

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

            consumer.subscribe(Arrays.asList(TEST_REQUEST_CHANNEL));
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    ConsumerRecords<String, String> records = consumer.poll(100);
                    for (ConsumerRecord<String, String> record : records) {
                        if(record.key().equals(correlationId + "::" + TEST_REPLY_CHANNEL)) {
                            Assert.assertEquals(new Gson().toJson(message), record.value());
                            RequesterTest.messageReceivedFlag = true;
                        }
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

        Assert.assertTrue(RequesterTest.messageReceivedFlag);
    }

    @Test
    public void receive() {
        // given
        new Thread(new MessageConsumer(TEST_REPLY_CHANNEL)).start();

        String correlationId =  java.util.UUID.randomUUID().toString().toUpperCase();

        Map<String, String> requestMessage = new HashMap<>();
        requestMessage.put("productId", "prd-1234");
        requestMessage.put("orderQty", "2");

        Requester requester = new Requester(TEST_REQUEST_CHANNEL, TEST_REPLY_CHANNEL, correlationId);
        requester.send(new Gson().toJson(requestMessage));

        new Thread(() -> {
            Properties props = new Properties();
            props.put("bootstrap.servers", "localhost:9092");
            props.put("group.id", "test");
            props.put("enable.auto.commit", "true");
            props.put("auto.commit.interval.ms", "1000");
            props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

            consumer.subscribe(Arrays.asList(TEST_REQUEST_CHANNEL));
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    ConsumerRecords<String, String> records = consumer.poll(100);
                    for (ConsumerRecord<String, String> record : records) {
                        if(record.key().equals(correlationId + "::" + TEST_REPLY_CHANNEL)) {
                            // Reply Message
                            Map<String, String> replyMessage = new HashMap<>();
                            replyMessage.put("validation", "SUCCESS");
                            MessageProducer producer = new MessageProducer(TEST_REPLY_CHANNEL);
                            producer.send(correlationId,  new Gson().toJson(replyMessage));
                        }
                    }
                }
            } finally {
                consumer.close();
            }
        }).start();

        // when
        String replyMessage = requester.receive();
        System.out.println(replyMessage);

        // then
        Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        Map<String, String> content = new Gson().fromJson(replyMessage, type);

        Assert.assertEquals(content.get("validation"), "SUCCESS");
    }
}
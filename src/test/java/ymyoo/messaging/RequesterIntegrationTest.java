package ymyoo.messaging;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 유영모 on 2016-12-23.
 */
public class RequesterIntegrationTest extends KafkaIntegrationTest {
    static boolean messageReceivedFlag = false;

    final String TEST_REQUEST_CHANNEL = "TEST-REQUEST";
    final String TEST_REPLY_CHANNEL = "TEST-REPLY";

    @Before
    public void setUp() throws Exception {
        RequesterIntegrationTest.messageReceivedFlag = false;
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
        assertSendingMessage(TEST_REQUEST_CHANNEL, correlationId + "::" + TEST_REPLY_CHANNEL, new Gson().toJson(message));
    }

    @Test
    public void receive() {
        // given
        //  - 응답 채널 리스닝...
        onListener(TEST_REPLY_CHANNEL);

        //  - 메시지 송신
        String correlationId =  java.util.UUID.randomUUID().toString().toUpperCase();
        Map<String, String> message = new HashMap<>();
        message.put("productId", "prd-1234");
        message.put("orderQty", "2");

        Requester requester = new Requester(TEST_REQUEST_CHANNEL, TEST_REPLY_CHANNEL, correlationId);
        requester.send(new Gson().toJson(message));

        //  - Reply Message 송신자 대기..
        onFakeReplier(correlationId);

        // when
        String replyMessage = requester.receive();

        // then
        Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        Map<String, String> content = new Gson().fromJson(replyMessage, type);

        Assert.assertEquals(content.get("validation"), "SUCCESS");
    }

    private void onListener(String channel) {
        new Thread(new ReplyMessageConsumer(channel)).start();
    }

    private void onFakeReplier(String correlationId) {
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
    }

}
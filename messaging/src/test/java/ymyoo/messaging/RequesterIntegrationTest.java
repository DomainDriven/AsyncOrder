package ymyoo.messaging;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("productId", "prd-1234");
        messageBody.put("orderQty", "2");

        // when
        Requester requester = new Requester(TEST_REQUEST_CHANNEL, TEST_REPLY_CHANNEL);
        requester.send(messageBody);

        // then
        assertSendingMessage(TEST_REQUEST_CHANNEL, requester.getCorrelationId(), message -> {
            Assert.assertEquals(TEST_REPLY_CHANNEL, message.getHeaders().get("returnAddress"));
            Assert.assertEquals(messageBody.get("productId"), ((Map)message.getBody()).get("productId"));
            Assert.assertEquals(messageBody.get("orderQty"), ((Map)message.getBody()).get("orderQty"));
        });
    }

    @Test
    public void receive() {
        // given
        //  - 응답 채널 리스닝...
        onListener(TEST_REPLY_CHANNEL);

        //  - 메시지 송신
        Map<String, String> message = new HashMap<>();
        message.put("productId", "prd-1234");
        message.put("orderQty", "2");

        Requester requester = new Requester(TEST_REQUEST_CHANNEL, TEST_REPLY_CHANNEL);
        requester.send(message);

        //  - Reply Message 송신자 대기..
        onFakeReplier(requester.getCorrelationId());

        // when
        Message replyMessage = requester.receive();

        // then
        Assert.assertEquals("SUCCESS", ((Map)replyMessage.getBody()).get("validation"));
    }

    private void onListener(String channel) {
        new Thread(new PollingMessageConsumer(channel)).start();
    }

    private void onFakeReplier(String messageId) {
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
                        Message receivedMessage = new Gson().fromJson(record.value(), Message.class);
                        if(receivedMessage.getMessageId().equals(messageId)) {
                            // Reply Message
                            Map<String, String> headers = new HashMap<>();
                            headers.put("correlationId", receivedMessage.getMessageId());

                            Map<String, String> body = new HashMap<>();
                            body.put("validation", "SUCCESS");

                            String sendMessageId = generateId();
                            Message sendMessage = new Message(sendMessageId, headers, body);

                            MessageProducer producer = new MessageProducer();
                            producer.send(receivedMessage.getHeaders().get("returnAddress"), sendMessage);
                        }
                    }
                }
            } finally {
                consumer.close();
            }
        }).start();
    }

}
package ymyoo.messaging.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by 유영모 on 2016-12-29.
 */
public class MessageConsumerIntegrationTest extends KafkaIntegrationTest {
    public static boolean messageReceivedFlag = false;

    @Before
    public void setUp() throws Exception {
        messageReceivedFlag = false;
    }

    @Test
    public void poll() throws Exception {
        // given
        final String channel = "TEST-REQUEST";
        final String messageId = java.util.UUID.randomUUID().toString().toUpperCase();
        final String messageBody = "test";

        // when
        sendMessage(channel, messageId, messageBody);

        // then
        new Thread(() -> {
            MessageConsumer messageConsumer = new MessageConsumer(channel);
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    List<Message> messages = messageConsumer.poll();
                    for(Message message : messages) {
                        if(message.getId().equals(messageId)) {
                            Assert.assertEquals(messageBody, message.getBody());
                            messageReceivedFlag = true;
                        }
                    }
                }
            } finally {
                messageConsumer.close();
            }
        }).start();

        waitCurrentThread(7);
        Assert.assertTrue(this.messageReceivedFlag);
    }

}
package ymyoo.infra.messaging;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by 유영모 on 2017-01-02.
 */
public class AbstractReplierTest extends KafkaIntegrationTest {

    @Test
    public void onMessage() throws Exception {
        // given
        final String requestChannel = "TEST-REQUEST";
        final String requestMessageId = generateId();
        final String requestMessageBody = "test";

        TestReplier testReplier = new TestReplier(requestChannel);
        new Thread(testReplier).start();

        // when
        sendMessage(requestChannel, new Message(requestMessageId, requestMessageBody));
        waitCurrentThread(5);

        // then
        boolean receivedMessageFlag = false;
        for(Message message : testReplier.getMessageList()) {
            if(message.getMessageId().equals(requestMessageId)) {
                Assert.assertEquals(requestMessageBody, message.getBody());
                receivedMessageFlag = true;
            }
        }

        Assert.assertTrue(receivedMessageFlag);
    }
}
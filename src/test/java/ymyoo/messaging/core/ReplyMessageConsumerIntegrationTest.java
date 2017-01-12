package ymyoo.messaging.core;

import org.junit.Assert;
import org.junit.Test;


/**
 * Created by 유영모 on 2016-12-06.
 */
public class ReplyMessageConsumerIntegrationTest extends KafkaIntegrationTest {

    @Test
    public void testRun() throws InterruptedException {
        // given
        String channelName = "TEST-REPLY";
        Thread r = new Thread(new ReplyMessageConsumer(channelName));
        r.start();

        String callback1Key = "12345";
        String callback1Value = "hello";

        ReplyMessageConsumer.registerListener(new MessageListener() {
            @Override
            public void onMessage(String message) {
                // then
                Assert.assertEquals(message, callback1Value);
            }

            @Override
            public String getCorrelationId() {
                return callback1Key;
            }
        });

        String callback2Key = "67890";
        String callback2Value = "bye!";

        ReplyMessageConsumer.registerListener(new MessageListener() {
            @Override
            public void onMessage(String message) {
                Assert.assertEquals(message, callback2Value);
            }

            @Override
            public String getCorrelationId() {
                return callback2Key;
            }
        });

        // when
        sendMessage("TEST-REPLY", callback1Key, callback1Value);
        sendMessage("TEST-REPLY", callback2Key, callback2Value);

        // 비동기 처리 대기
        waitCurrentThread(5);
    }

}
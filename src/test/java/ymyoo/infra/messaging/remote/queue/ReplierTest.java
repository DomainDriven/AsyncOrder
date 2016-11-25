package ymyoo.infra.messaging.remote.queue;

import org.junit.Assert;
import org.junit.Test;
import ymyoo.infra.messaging.remote.queue.message.Message;
import ymyoo.infra.messaging.remote.queue.message.MessageHead;
import ymyoo.order.domain.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 유영모 on 2016-11-16.
 */
public class ReplierTest {

    @Test
    public void testReply() {
        // given
        Thread r = new Thread(new Replier());
        r.start();

        String orderId = OrderIdGenerator.generate();
        MessageHead messageHead = new MessageHead(orderId, MessageHead.MessageType.CHECK_INVENTOY);
        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("deliveryType", "aaaa");
        messageBody.put("proudctId", "11111");
        messageBody.put("orderQty", "1");

        // when
        Requester requester = new Requester();
        requester.send(new Message(messageHead, messageBody));

        // then
        Message receivedMessage = requester.receive(orderId);
        Assert.assertEquals("SUCCESS", receivedMessage.getBody().get("validation"));
    }
}

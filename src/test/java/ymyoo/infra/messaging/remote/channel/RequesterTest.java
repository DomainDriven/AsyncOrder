package ymyoo.infra.messaging.remote.channel;

import org.junit.Assert;
import org.junit.Test;
import ymyoo.infra.messaging.remote.channel.message.Message;
import ymyoo.infra.messaging.remote.channel.message.MessageHead;
import ymyoo.order.domain.*;
import ymyoo.infra.messaging.remote.channel.blockingqueue.ReplyBlockingQueue;
import ymyoo.infra.messaging.remote.channel.blockingqueue.RequestBlockingQueue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Created by 유영모 on 2016-11-16.
 */
public class RequesterTest {

    @Test
    public void testSend() throws InterruptedException {
        // given
        String orderId = OrderIdGenerator.generate();

        MessageHead head = new MessageHead(orderId, MessageHead.MessageType.CHECK_INVENTOY);
        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("test", "test");

        // when
        Requester requester = new Requester();
        Message requestMessage = new Message(head, messageBody);
        requester.send(requestMessage);

        // then
        BlockingQueue<Message> queue = RequestBlockingQueue.getBlockingQueue();
        Message actual = queue.take();

        Assert.assertEquals(requestMessage, actual);
    }

    @Test
    public void testReceive() throws InterruptedException {
        // given
        String orderId = OrderIdGenerator.generate();

        MessageHead head = new MessageHead(orderId, MessageHead.MessageType.CHECK_INVENTOY);
        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("test", "test");

        ReplyBlockingQueue.getBlockingQueue().add(new Message(head, messageBody));

        // when
        Requester requester = new Requester();
        Message receivedMessage = requester.receive(orderId);

        // then
        Assert.assertEquals(receivedMessage.getBody().get("test"), "test");
    }
}

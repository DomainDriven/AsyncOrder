package ymyoo.infra.messaging.remote.queue;

import org.junit.Assert;
import org.junit.Test;
import ymyoo.order.domain.*;
import ymyoo.infra.messaging.remote.queue.blockingqueue.ReplyBlockingQueue;
import ymyoo.infra.messaging.remote.queue.blockingqueue.RequestBlockingQueue;

import java.util.concurrent.BlockingQueue;

/**
 * Created by 유영모 on 2016-11-16.
 */
public class RequesterTest {

    @Test
    public void testSend() throws InterruptedException {
        // given
        Order order = OrderFactory.create(new OrderItem("P0001", 2, OrderItemDeliveryType.AGENCY),  new OrderPayment(2000, "123-456-0789"));
        String orderId = OrderIdGenerator.generate();

        RequestMessage requestMessage = new RequestMessage(orderId, order.getOrderItem(), RequestMessage.MessageType.CHECK_INVENTOY);

        // when
        Requester requester = new Requester();
        requester.send(requestMessage);

        // then
        BlockingQueue<Message> queue = RequestBlockingQueue.getBlockingQueue();
        RequestMessage actual = (RequestMessage)queue.take();

        Assert.assertEquals(requestMessage, actual);
    }

    @Test
    public void testReceive() throws InterruptedException {
        // given
        String orderId = OrderIdGenerator.generate();
        ReplyBlockingQueue.getBlockingQueue().add(new ReplyMessage(orderId, null, ReplyMessage.ReplyMessageStatus.SUCCESS));

        // when
        Requester requester = new Requester();
        ReplyMessage receivedRequestMessage = requester.receive(orderId);

        // then
        Assert.assertEquals(ReplyMessage.ReplyMessageStatus.SUCCESS, receivedRequestMessage.getStatus());
    }
}

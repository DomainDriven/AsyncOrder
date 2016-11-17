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

        Message sendMessage = new Message();
        String orderId = OrderIdGenerator.generate();
        sendMessage.setId(orderId);
        sendMessage.setType(MessageType.CHECK_INVENTOY);
        sendMessage.setObjectProperty(order.getOrderItem());

        // when
        Requester requester = new Requester();
        requester.send(sendMessage);

        // then
        BlockingQueue<Message> queue = RequestBlockingQueue.getBlockingQueue();
        Message retrievedMessage = queue.take();

        Assert.assertEquals(sendMessage, retrievedMessage);
    }

    @Test
    public void testReceive() throws InterruptedException {
        // given
        Order order = OrderFactory.create(new OrderItem("P0001", 2, OrderItemDeliveryType.AGENCY),  new OrderPayment(2000, "123-456-0789"));

        Message sendMessage = new Message();
        String orderId = OrderIdGenerator.generate();
        sendMessage.setId(orderId);
        sendMessage.setType(MessageType.CHECK_INVENTOY);
        sendMessage.setObjectProperty(order.getOrderItem());

        // when
        Requester requester = new Requester();
        requester.send(sendMessage);

        ReplyBlockingQueue.getBlockingQueue().put(new Message(orderId));

        Message receivedMessage = requester.receive(orderId);

        // then
        Assert.assertEquals(sendMessage.getId(), receivedMessage.getId());
    }
}

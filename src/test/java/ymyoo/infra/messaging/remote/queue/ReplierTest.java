package ymyoo.infra.messaging.remote.queue;

import org.junit.Assert;
import org.junit.Test;
import ymyoo.order.domain.*;

/**
 * Created by 유영모 on 2016-11-16.
 */
public class ReplierTest {

    @Test
    public void testReply() {
        // given
        Thread r = new Thread(new Replier());
        r.start();

        Order order = OrderFactory.create(new OrderItem("P0001", 2, OrderItemDeliveryType.AGENCY),  new OrderPayment(2000, "123-456-0789"));

        String orderId = OrderIdGenerator.generate();
        RequestMessage requestMessage = new RequestMessage(orderId, order.getOrderItem(), RequestMessage.MessageType.CHECK_INVENTOY);

        // when
        Requester requester = new Requester();
        requester.send(requestMessage);

        // then
        ReplyMessage replyMessage = requester.receive(orderId);
        Assert.assertEquals(ReplyMessage.ReplyMessageStatus.SUCCESS, replyMessage.getStatus());
    }
}

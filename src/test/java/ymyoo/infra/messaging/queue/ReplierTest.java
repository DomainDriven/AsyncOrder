package ymyoo.infra.messaging.queue;

import org.junit.Assert;
import org.junit.Test;
import ymyoo.domain.order.*;

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

        Message sendMessage = new Message();
        String orderId = OrderIdGenerator.generate();
        sendMessage.setId(orderId);
        sendMessage.setType(MessageType.CHECK_INVENTOY);
        sendMessage.setObjectProperty(order.getOrderItem());

        // when
        Requester requester = new Requester();
        requester.send(sendMessage);


        // then
        Message receivedMessage = requester.receive(orderId);
        Assert.assertEquals(sendMessage.getId(), receivedMessage.getId());
    }
}

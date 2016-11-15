package ymyoo.infra.messaging.queue;

import org.junit.Assert;
import org.junit.Test;
import ymyoo.domain.order.*;
import ymyoo.infra.messaging.queue.impl.RequestBlockingQueue;

/**
 * Created by 유영모 on 2016-11-15.
 */
public class MessageProducerTest {

    @Test
    public void testSend() {
        // given
        Order order = OrderFactory.create(new OrderItem("P0001", 2, OrderItemDeliveryType.AGENCY),  new OrderPayment(2000, "123-456-0789"));

        MessageProducer producer = new MessageProducer();

        Message sendMessage = new Message();
        String orderId = OrderIdGenerator.generate();
        sendMessage.setId(orderId);
        sendMessage.setType(MessageType.CHECK_INVENTOY);
        sendMessage.setObjectProperty(order.getOrderItem());

        // when
        producer.send(sendMessage);

        // then
        RequestBlockingQueue queue = RequestBlockingQueue.getInstance();
        Message retrievedMessage = queue.take();

        Assert.assertEquals(sendMessage, retrievedMessage);
    }
}

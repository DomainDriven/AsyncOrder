package ymyoo.order.adapter;

import ymyoo.order.domain.OrderItem;
import ymyoo.order.infra.messaging.queue.Message;
import ymyoo.order.infra.messaging.queue.MessageType;
import ymyoo.order.infra.messaging.queue.Requester;

/**
 * Created by 유영모 on 2016-11-17.
 */
public class InventoryAdapter {
    public void checkAndReserveOrderItem(final String orderId, final OrderItem orderItem) {
        Message sendMessage = new Message();
        sendMessage.setId(orderId);
        sendMessage.setType(MessageType.CHECK_INVENTOY);
        sendMessage.setObjectProperty(orderItem);

        Requester requester = new Requester();
        requester.send(sendMessage);
        Message receivedMessage = requester.receive(orderId);

        if(receivedMessage == null) {
            throw new RuntimeException("재고 오류");
        }
    }
}

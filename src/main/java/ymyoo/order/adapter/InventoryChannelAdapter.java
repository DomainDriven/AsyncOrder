package ymyoo.order.adapter;

import ymyoo.infra.messaging.remote.queue.ReplyMessage;
import ymyoo.order.domain.OrderItem;
import ymyoo.infra.messaging.remote.queue.RequestMessage;
import ymyoo.infra.messaging.remote.queue.Requester;

/**
 * Inventory와 인터렉션을 담당하는 어뎁터
 *
 * Created by 유영모 on 2016-11-17.
 */
public class InventoryChannelAdapter {
    private Requester requester = new Requester();

    public void sendCheckingAndReservingOrderItem(final String orderId, final OrderItem orderItem) {
        RequestMessage requestMessage = new RequestMessage(orderId, orderItem, RequestMessage.MessageType.CHECK_INVENTOY);
        requester.send(requestMessage);
    }

    public ReplyMessage listen(String orderId) {
        return requester.receive(orderId);
    }
}

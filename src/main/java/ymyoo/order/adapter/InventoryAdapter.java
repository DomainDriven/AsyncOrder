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
public class InventoryAdapter {
    public void checkAndReserveOrderItem(final String orderId, final OrderItem orderItem) {
        RequestMessage requestMessage = new RequestMessage(orderId, orderItem, RequestMessage.MessageType.CHECK_INVENTOY);

        Requester requester = new Requester();
        requester.send(requestMessage);
        ReplyMessage replyMessage = requester.receive(orderId);

        if(replyMessage.getStatus() == ReplyMessage.ReplyMessageStatus.FAILURE) {
            throw new RuntimeException("재고 오류");
        }
    }
}

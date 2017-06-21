package ymyoo.app.order.adapter.messaging;

import ymyoo.app.order.domain.command.OrderItem;
import ymyoo.infra.messaging.core.MessageChannels;
import ymyoo.infra.messaging.core.Message;
import ymyoo.infra.messaging.core.Requester;

import java.util.HashMap;
import java.util.Map;

/**
 * 재고 채널 어뎁터
 *
 * Created by 유영모 on 2016-11-17.
 */
public class InventoryChannelAdapter {

    public boolean checkAndReserveOrderItem(final String orderId, final OrderItem orderItem) {
        // 메시지 생성
        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("orderId", orderId);
        messageBody.put("deliveryType", orderItem.getDeliveryType().name());
        messageBody.put("productId", orderItem.getProductId());
        messageBody.put("orderQty", String.valueOf(orderItem.getOrderQty()));

        // 메시지 발신
        Requester requester = new Requester(MessageChannels.INVENTORY_REQUEST, MessageChannels.INVENTORY_REPLY);
        requester.send(messageBody);

        // 메시지 수신
        Message receivedMessage = requester.receive();

        return MessageTranslator.translate(receivedMessage);
    }

    static class MessageTranslator {
        public static boolean translate(Message message) {
            if(((Map)message.getBody()).get("validation").equals("SUCCESS")) {
                return true;
            } else {
                return false;
            }
        }
    }
}

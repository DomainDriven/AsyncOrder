package ymyoo.order.message.endpoint;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ymyoo.infra.messaging.remote.channel.message.Message;
import ymyoo.infra.messaging.remote.channel.message.MessageHead;
import ymyoo.order.domain.OrderItem;
import ymyoo.infra.messaging.remote.channel.Requester;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 재고 채널 어뎁터
 *
 * Created by 유영모 on 2016-11-17.
 */
public class InventoryChannelAdapter {
    private Requester requester = new Requester();

    public boolean checkAndReserveOrderItem(final String orderId, final OrderItem orderItem) {
        // 메시지 발신
        String messageId = java.util.UUID.randomUUID().toString().toUpperCase();
        MessageHead messageHead = new MessageHead(messageId, MessageHead.MessageType.CHECK_INVENTOY);
        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("deliveryType", orderItem.getDeliveryType().name());
        messageBody.put("productId", orderItem.getProductId());
        messageBody.put("orderQty", String.valueOf(orderItem.getOrderQty()));

        requester.send(new Message(messageHead, new Gson().toJson(messageBody)));

        // 메시지 수신
        Message receivedMessage = requester.receive(messageId);
        return MessageTranslator.translate(receivedMessage);
    }

    static class MessageTranslator {
        public static boolean translate(Message message) {
            Type type = new TypeToken<HashMap<String, String>>(){}.getType();
            Map<String, String> content = new Gson().fromJson(message.getBody(), type);
            if(content.get("validation").equals("SUCCESS")) {
                return true;
            } else {
                return false;
            }
        }
    }
}

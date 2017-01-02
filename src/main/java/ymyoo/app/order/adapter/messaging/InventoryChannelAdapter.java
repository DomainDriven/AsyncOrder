package ymyoo.app.order.adapter.messaging;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ymyoo.messaging.MessageChannels;
import ymyoo.app.order.domain.so.SalesOrderItem;
import ymyoo.messaging.Requester;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 재고 채널 어뎁터
 *
 * Created by 유영모 on 2016-11-17.
 */
public class InventoryChannelAdapter {

    public boolean checkAndReserveOrderItem(final SalesOrderItem orderItem) {
        // 메시지 생성
        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("deliveryType", orderItem.getDeliveryType().name());
        messageBody.put("productId", orderItem.getProductId());
        messageBody.put("orderQty", String.valueOf(orderItem.getOrderQty()));

        // 메시지 발신
        String correlationId =  java.util.UUID.randomUUID().toString().toUpperCase();
        Requester requester = new Requester(MessageChannels.INVENTORY_REQUEST, MessageChannels.INVENTORY_REPLY, correlationId);

        requester.send(new Gson().toJson(messageBody));

        // 메시지 수신
        String receivedMessage = requester.receive();

        return MessageTranslator.translate(receivedMessage);
    }

    static class MessageTranslator {
        public static boolean translate(String message) {
            Type type = new TypeToken<HashMap<String, String>>(){}.getType();
            Map<String, String> content = new Gson().fromJson(message, type);
            if(content.get("validation").equals("SUCCESS")) {
                return true;
            } else {
                return false;
            }
        }
    }
}

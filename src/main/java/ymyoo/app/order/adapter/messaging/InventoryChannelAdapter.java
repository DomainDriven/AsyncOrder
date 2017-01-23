package ymyoo.app.order.adapter.messaging;

import com.google.gson.Gson;
import ymyoo.app.order.domain.OrderItem;
import ymyoo.messaging.core.Callback;
import ymyoo.messaging.core.CallbackMessageConsumer;
import ymyoo.messaging.core.MessageChannels;
import ymyoo.messaging.core.Requester;

import java.util.HashMap;
import java.util.Map;

/**
 * 재고 채널 어뎁터
 *
 * Created by 유영모 on 2016-11-17.
 */
public class InventoryChannelAdapter {

    public void checkAndReserveOrderItem(final String correlationId, final String orderId, final OrderItem orderItem, Callback callback) {
        // 메시지 생성
        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("orderId", orderId);
        messageBody.put("deliveryType", orderItem.getDeliveryType().name());
        messageBody.put("productId", orderItem.getProductId());
        messageBody.put("orderQty", String.valueOf(orderItem.getOrderQty()));

        // 메시지 발신
        //String correlationId =  java.util.UUID.randomUUID().toString().toUpperCase();
        Requester requester = new Requester(MessageChannels.INVENTORY_REQUEST, MessageChannels.INVENTORY_REPLY, correlationId);

        requester.send(new Gson().toJson(messageBody));

        // 메시지 처리 후 응답 콜백 등록
        CallbackMessageConsumer.registerCallback(callback);
    }
}

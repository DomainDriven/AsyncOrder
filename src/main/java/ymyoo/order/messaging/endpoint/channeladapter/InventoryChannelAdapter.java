package ymyoo.order.messaging.endpoint.channeladapter;

import com.google.gson.Gson;
import ymyoo.infra.messaging.remote.channel.Callback;
import ymyoo.infra.messaging.remote.channel.MessageChannel;
import ymyoo.infra.messaging.remote.channel.MessageConsumer;
import ymyoo.infra.messaging.remote.channel.MessageProducer;
import ymyoo.order.domain.so.SalesOrderItem;

import java.util.HashMap;
import java.util.Map;

/**
 * 재고 채널 어뎁터
 *
 * Created by 유영모 on 2016-11-17.
 */
public class InventoryChannelAdapter {

    public void checkAndReserveOrderItem(final String id, final SalesOrderItem orderItem, Callback callback) {
        // 메시지 생성
        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("deliveryType", orderItem.getDeliveryType().name());
        messageBody.put("productId", orderItem.getProductId());
        messageBody.put("orderQty", String.valueOf(orderItem.getOrderQty()));

        // 메시지 발신
        MessageProducer producer = new MessageProducer(MessageChannel.INVENTORY_REQUEST, MessageChannel.INVENTORY_REPLY);
        producer.send(id, new Gson().toJson(messageBody));

        // 메시지 처리 후 응답 콜백 등록
        MessageConsumer.registerCallback(callback);
    }
}

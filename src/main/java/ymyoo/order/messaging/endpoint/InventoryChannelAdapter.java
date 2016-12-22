package ymyoo.order.messaging.endpoint;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ymyoo.infra.messaging.remote.channel.Callback;
import ymyoo.infra.messaging.remote.channel.MessageChannel;
import ymyoo.infra.messaging.remote.channel.MessageConsumer;
import ymyoo.infra.messaging.remote.channel.MessageProducer;
import ymyoo.order.domain.so.SalesOrderItem;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 재고 채널 어뎁터
 *
 * Created by 유영모 on 2016-11-17.
 */
public class InventoryChannelAdapter implements Callback<Boolean> {
    private final String id = java.util.UUID.randomUUID().toString().toUpperCase();
    private boolean result = false;

    public synchronized boolean checkAndReserveOrderItem(final SalesOrderItem orderItem) {
        // 메시지 생성
        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("deliveryType", orderItem.getDeliveryType().name());
        messageBody.put("productId", orderItem.getProductId());
        messageBody.put("orderQty", String.valueOf(orderItem.getOrderQty()));

        // 메시지 발신
        MessageProducer producer = new MessageProducer(MessageChannel.INVENTORY_REQUEST, MessageChannel.INVENTORY_REPLY);
        producer.send(id, new Gson().toJson(messageBody));

        // 메시지 처리 후 응답 콜백 등록
        MessageConsumer.registerCallback(this);
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public synchronized void call(Boolean result) {
        this.result = result;
        this.notify();
    }

    @Override
    public Boolean translate(String data) {
        Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        Map<String, String> content = new Gson().fromJson(data, type);
        if(content.get("validation").equals("SUCCESS")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getId() {
        return id;
    }
}

package ymyoo.messaging.core.interceptor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import ymyoo.app.order.domain.OrderStatus;
import ymyoo.messaging.core.LogOrderStatusChannelAdapter;
import ymyoo.messaging.core.MessageChannels;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 유영모 on 2017-01-16.
 */
public class MessageProducerInterceptorImpl implements ProducerInterceptor {
    private LogOrderStatusChannelAdapter adapter = new LogOrderStatusChannelAdapter();

    @Override
    public ProducerRecord onSend(ProducerRecord producerRecord) {
        storeMessage(producerRecord);
        return producerRecord;
    }

    private void storeMessage(ProducerRecord producerRecord) {
        if(producerRecord.topic().equals(MessageChannels.INVENTORY_REQUEST)) {
            requestStoringOrderStatus(producerRecord, OrderStatus.Status.INVENTORY_CHECKED_REQUEST);
        } else if(producerRecord.topic().equals(MessageChannels.PAYMENT_AUTH_APP_REQUEST)) {
            requestStoringOrderStatus(producerRecord, OrderStatus.Status.PAYMENT_REQUEST);
        } else if(producerRecord.topic().equals(MessageChannels.PURCHASE_ORDER_CREATED)) {
            requestStoringOrderStatus(producerRecord, OrderStatus.Status.PURCHASE_ORDER_CREATED);
        } else if(producerRecord.topic().equals(MessageChannels.INVENTORY_REPLY)) {
            requestStoringOrderStatus(producerRecord, OrderStatus.Status.INVENTORY_CHECKED);
        }  else if(producerRecord.topic().equals(MessageChannels.PAYMENT_AUTH_APP_REPLY)) {
            requestStoringOrderStatus(producerRecord, OrderStatus.Status.PAYMENT_DONE);
        }
    }

    private void requestStoringOrderStatus(ProducerRecord producerRecord, OrderStatus.Status inventoryCheckedRequest) {
        Type type = new TypeToken<HashMap<String, Object>>() {}.getType();
        Map<String, String> content = new Gson().fromJson(producerRecord.value().toString(), type);
        String orderId = content.get("orderId");

        OrderStatus orderStatus = new OrderStatus(orderId, inventoryCheckedRequest);
        adapter.logStatus(orderStatus);
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}

package ymyoo.messaging.core.interceptor;

import com.google.gson.Gson;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import ymyoo.app.order.domain.OrderStatus;
import ymyoo.messaging.adapter.MessageStoreChannelAdapter;
import ymyoo.messaging.core.Message;
import ymyoo.messaging.core.MessageChannels;
import ymyoo.messaging.processor.entitiy.OrderStatusEntity;

import java.util.Map;

/**
 * Created by 유영모 on 2017-01-16.
 */
public class MessageProducerInterceptorImpl implements ProducerInterceptor {
    private MessageStoreChannelAdapter adapter = new MessageStoreChannelAdapter();

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
        final Message message = new Gson().fromJson(producerRecord.value().toString(), Message.class);
        final String orderId = (String)((Map)message.getBody()).get("orderId");

        OrderStatusEntity orderStatus = new OrderStatusEntity(orderId, OrderStatusEntity.Status.valueOf(inventoryCheckedRequest.name()));
        adapter.storeOrderStatus(orderStatus);
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

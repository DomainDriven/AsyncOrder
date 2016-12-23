package ymyoo.order.messaging.endpoint.channeladapter;

import com.google.gson.Gson;
import ymyoo.infra.messaging.remote.channel.MessageChannel;
import ymyoo.infra.messaging.remote.channel.MessageProducer;
import ymyoo.order.domain.po.PurchaseOrder;

/**
 * Created by 유영모 on 2016-12-15.
 */
public class PurchaseOrderChannelAdapter {
    private MessageProducer messageProducer = null;

    public void setMessageProducer(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    public void onPurchaseOrderCreated(String id, PurchaseOrder purchaseOrder) {
        // 메시지 발신
        if(messageProducer == null) {
            messageProducer = new MessageProducer(MessageChannel.PURCHASE_ORDER_CREATED);
        }

        messageProducer.send(id, new Gson().toJson(purchaseOrder));
    }
}

package ymyoo.order.messaging.endpoint.channeladapter;

import com.google.gson.Gson;
import ymyoo.infra.messaging.remote.channel.MessageChannel;
import ymyoo.order.domain.po.PurchaseOrder;
import ymyoo.order.messaging.endpoint.request.Requester;

/**
 * Created by 유영모 on 2016-12-15.
 */
public class PurchaseOrderChannelAdapter {

    public void onPurchaseOrderCreated(PurchaseOrder purchaseOrder) {
        // 구매 주문 생성 완료 이벤트 송신
        String correlationId =  java.util.UUID.randomUUID().toString().toUpperCase();
        Requester requester = new Requester(MessageChannel.PURCHASE_ORDER_CREATED, correlationId);
        requester.send(new Gson().toJson(new Gson().toJson(purchaseOrder)));
    }
}

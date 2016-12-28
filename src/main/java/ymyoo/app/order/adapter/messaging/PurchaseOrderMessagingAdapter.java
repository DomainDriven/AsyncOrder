package ymyoo.app.order.adapter.messaging;

import com.google.gson.Gson;
import ymyoo.messaging.MessageChannels;
import ymyoo.app.order.domain.po.PurchaseOrder;
import ymyoo.messaging.Requester;

/**
 * Created by 유영모 on 2016-12-15.
 */
public class PurchaseOrderMessagingAdapter {

    public void onPurchaseOrderCreated(PurchaseOrder purchaseOrder) {
        // 구매 주문 생성 완료 이벤트 송신
        String correlationId =  java.util.UUID.randomUUID().toString().toUpperCase();
        Requester requester = new Requester(MessageChannels.PURCHASE_ORDER_CREATED, correlationId);
        requester.send(new Gson().toJson(new Gson().toJson(purchaseOrder)));
    }
}

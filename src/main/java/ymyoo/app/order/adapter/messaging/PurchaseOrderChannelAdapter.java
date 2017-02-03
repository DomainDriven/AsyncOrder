package ymyoo.app.order.adapter.messaging;

import ymyoo.app.order.domain.po.PurchaseOrder;
import ymyoo.messaging.core.MessageChannels;
import ymyoo.messaging.core.Requester;

/**
 * Created by 유영모 on 2016-12-15.
 */
public class PurchaseOrderChannelAdapter {

    public void onPurchaseOrderCreated(PurchaseOrder purchaseOrder) {
        // 구매 주문 생성 완료 이벤트 송신
        Requester requester = new Requester(MessageChannels.PURCHASE_ORDER_CREATED);
        requester.send(purchaseOrder);
    }
}

package ymyoo.order.purchase;

import ymyoo.order.OrderItemDeliveryType;
import ymyoo.order.purchase.impl.DefaultPurchaseOrder;
import ymyoo.order.purchase.impl.DirectDeliveryPurchaseOrder;

/**
 * 구매 주문 팩토리
 *
 * Created by 유영모 on 2016-10-20.
 */
public class PurchaseOrderFactory {

    public static PurchaseOrder create(OrderItemDeliveryType deliveryType) {
        if(deliveryType == OrderItemDeliveryType.DIRECTING) {
            return new DirectDeliveryPurchaseOrder(new DefaultPurchaseOrder());
        } else {
            return new DefaultPurchaseOrder();
        }
    }
}

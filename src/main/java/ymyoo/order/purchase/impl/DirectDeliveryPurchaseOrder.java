package ymyoo.order.purchase.impl;

import ymyoo.order.Order;
import ymyoo.order.payment.ApprovalOrderPayment;
import ymyoo.order.purchase.PurchaseOrder;
import ymyoo.util.PrettySystemOut;

/**
 * 자사 배송 상품 구매 주문
 * Created by 유영모 on 2016-10-19.
 */
public class DirectDeliveryPurchaseOrder extends PurchaseOrderDecorator {
    public DirectDeliveryPurchaseOrder(PurchaseOrder purchaseOrder) {
        super(purchaseOrder);
    }

    @Override
    public void create(Order order, ApprovalOrderPayment approvalOrderPayment) {
        super.create(order, approvalOrderPayment);
        PrettySystemOut.println(DirectDeliveryPurchaseOrder.class, "물류 창고 번호와 구매 주문을 매핑..");
    }
}

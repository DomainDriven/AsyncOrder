package ymyoo.domain.purchaseorder.impl;

import ymyoo.domain.order.Order;
import ymyoo.domain.payment.ApprovalOrderPayment;
import ymyoo.domain.purchaseorder.PurchaseOrder;

/**
 * 구매 주문 데코레이터
 *
 * Created by 유영모 on 2016-10-19.
 */
public abstract class PurchaseOrderDecorator implements PurchaseOrder {
    protected PurchaseOrder purchaseOrder;

    public PurchaseOrderDecorator(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    @Override
    public void create(Order order, ApprovalOrderPayment approvalOrderPayment) {
        this.purchaseOrder.create(order, approvalOrderPayment);
    }
}

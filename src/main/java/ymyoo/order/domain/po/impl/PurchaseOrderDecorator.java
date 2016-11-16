package ymyoo.order.domain.po.impl;

import ymyoo.order.domain.Order;
import ymyoo.payment.ApprovalOrderPayment;
import ymyoo.order.domain.po.PurchaseOrder;

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

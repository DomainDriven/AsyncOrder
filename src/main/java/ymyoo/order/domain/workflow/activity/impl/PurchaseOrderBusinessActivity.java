package ymyoo.order.domain.workflow.activity.impl;

import ymyoo.order.domain.ApprovalOrderPayment;
import ymyoo.order.domain.Order;
import ymyoo.order.domain.workflow.activity.AsyncBusinessActivity;
import ymyoo.order.domain.po.PurchaseOrder;
import ymyoo.order.domain.workflow.activity.SyncBusinessActivity;
import ymyoo.utility.PrettySystemOut;

/**
 * Created by 유영모 on 2016-10-20.
 */
public class PurchaseOrderBusinessActivity implements SyncBusinessActivity<ApprovalOrderPayment, Void> {
    private Order order;
    private PurchaseOrder purchaseOrder;

    public PurchaseOrderBusinessActivity(Order order, PurchaseOrder purchaseOrder) {
        this.order = order;
        this.purchaseOrder = purchaseOrder;
    }

    @Override
    public Void perform(ApprovalOrderPayment approvalOrderPayment) {
        purchaseOrder.create(order,  approvalOrderPayment);
        PrettySystemOut.println(order.getClass(), "주문 완료....");

        return null;
    }
}

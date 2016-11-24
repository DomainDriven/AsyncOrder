package ymyoo.order.domain.workflow.activity.impl;

import ymyoo.order.domain.Order;
import ymyoo.order.domain.workflow.activity.SyncActivity;
import ymyoo.payment.ApprovalOrderPayment;
import ymyoo.order.domain.po.PurchaseOrder;
import ymyoo.order.utility.PrettySystemOut;

/**
 * Created by 유영모 on 2016-10-20.
 */
public class PurchaseOrderSyncActivity implements SyncActivity<Void> {
    private Order order;
    private PurchaseOrder purchaseOrder;
    private ApprovalOrderPayment approvalOrderPayment;

    public PurchaseOrderSyncActivity(Order order, PurchaseOrder purchaseOrder, ApprovalOrderPayment approvalOrderPayment) {
        this.order = order;
        this.purchaseOrder = purchaseOrder;
        this.approvalOrderPayment = approvalOrderPayment;
    }

    @Override
    public Void perform() {
        purchaseOrder.create(order,  approvalOrderPayment);
        PrettySystemOut.println(order.getClass(), "주문 완료....");

        return null;
    }
}

package ymyoo.order.domain.workflow.activity.impl;

import ymyoo.order.domain.ApprovalOrderPayment;
import ymyoo.order.domain.Order;
import ymyoo.order.domain.workflow.activity.BusinessActivity;
import ymyoo.payment.ApprovalPayment;
import ymyoo.order.domain.po.PurchaseOrder;
import ymyoo.utility.PrettySystemOut;

/**
 * Created by 유영모 on 2016-10-20.
 */
public class PurchaseOrderSyncActivity implements BusinessActivity<ApprovalOrderPayment, Void> {
    private Order order;
    private PurchaseOrder purchaseOrder;

    public PurchaseOrderSyncActivity(Order order, PurchaseOrder purchaseOrder) {
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

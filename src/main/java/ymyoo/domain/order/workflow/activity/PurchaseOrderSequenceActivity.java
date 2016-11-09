package ymyoo.domain.order.workflow.activity;

import ymyoo.domain.order.Order;
import ymyoo.domain.order.event.OrderCompleted;
import ymyoo.domain.payment.ApprovalOrderPayment;
import ymyoo.domain.purchaseorder.PurchaseOrder;
import ymyoo.infra.messaging.EventPublisher;
import ymyoo.util.PrettySystemOut;

import java.util.function.BiFunction;

/**
 * Created by 유영모 on 2016-10-20.
 */
public class PurchaseOrderSequenceActivity implements SequenceActivity<Void> {
    private Order order;
    private PurchaseOrder purchaseOrder;
    private ApprovalOrderPayment approvalOrderPayment;

    public PurchaseOrderSequenceActivity(Order order, PurchaseOrder purchaseOrder, ApprovalOrderPayment approvalOrderPayment) {
        this.order = order;
        this.purchaseOrder = purchaseOrder;
        this.approvalOrderPayment = approvalOrderPayment;
    }

    @Override
    public Void act() {
        purchaseOrder.create(order,  approvalOrderPayment);
        PrettySystemOut.println(order.getClass(), "주문 완료....");

        return null;
    }
}

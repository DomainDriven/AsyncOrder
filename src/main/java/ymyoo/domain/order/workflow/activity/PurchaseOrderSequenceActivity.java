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
public class PurchaseOrderSequenceActivity implements BiFunction<Void, ApprovalOrderPayment, Void> {
    private Order order;
    private PurchaseOrder purchaseOrder;

    public PurchaseOrderSequenceActivity(Order order, PurchaseOrder purchaseOrder) {
        this.order = order;
        this.purchaseOrder = purchaseOrder;
    }

    @Override
    public Void apply(Void aVoid, ApprovalOrderPayment approvalOrderPayment) {
        // 구매 주문 생성
        purchaseOrder.create(order,  approvalOrderPayment);
        PrettySystemOut.println(order.getClass(), "주문 완료....");

        // 주문 완료 이벤트 발행
        EventPublisher.instance().publish(new OrderCompleted(order.getOrderId()));
        return null;
    }
}

package ymyoo.order.purchase;

import ymyoo.order.Order;
import ymyoo.order.OrderItemDeliveryType;
import ymyoo.order.event.OrderCompleted;
import ymyoo.messaging.EventPublisher;
import ymyoo.order.payment.ApprovalOrderPayment;
import ymyoo.util.PrettySystemOut;

import java.util.function.BiFunction;

/**
 * Created by 유영모 on 2016-10-20.
 */
public class PurchaseOrderSequence implements BiFunction<Void, ApprovalOrderPayment, Void> {
    private Order order;

    public PurchaseOrderSequence(Order order) {
        this.order = order;
    }

    @Override
    public Void apply(Void aVoid, ApprovalOrderPayment approvalOrderPayment) {
        // 구매 주문 생성
        PurchaseOrder purchaseOrder;

        if(order.getOrderItem().getDeliveryType() == OrderItemDeliveryType.DIRECTING) {
            purchaseOrder = new DirectDeliveryPurchaseOrder(new DefaultPurchaseOrder());
        } else {
            purchaseOrder = new DefaultPurchaseOrder();
        }

        purchaseOrder.create(order,  approvalOrderPayment);
        PrettySystemOut.println(order.getClass(), "주문 완료....");

        // 주문 완료 이벤트 발행
        EventPublisher.instance().publish(new OrderCompleted(order.getOrderId()));
        return null;
    }
}

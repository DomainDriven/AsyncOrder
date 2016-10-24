package ymyoo.domain.order.workflow;

import ymyoo.domain.inventory.exception.StockOutException;
import ymyoo.domain.inventory.impl.DirectingInventory;
import ymyoo.domain.order.Order;
import ymyoo.domain.order.event.OrderFailed;
import ymyoo.domain.order.workflow.activity.InventorySequenceActivity;
import ymyoo.domain.order.workflow.activity.PaymentGatewaySequenceActivity;
import ymyoo.domain.order.workflow.activity.PurchaseOrderSequenceActivity;
import ymyoo.domain.payment.ApprovalOrderPayment;
import ymyoo.domain.purchaseorder.impl.DefaultPurchaseOrder;
import ymyoo.domain.purchaseorder.impl.DirectDeliveryPurchaseOrder;
import ymyoo.infra.messaging.EventPublisher;
import ymyoo.util.PrettySystemOut;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

/**
 * 자사 배송 상품 프로세서
 *
 * Created by 유영모 on 2016-10-24.
 */
public class DirectingDeliveryProductProcessor implements OrderProcess<Order> {

    @Override
    public void runWorkflow(Order order) {
        // Activity를 조합하여 Workflow를 만든다.
        /**
         * 비동기 Workflow
         *
         * 1. 재고 확인/예약 작업과 결제 인증/승인 작업 동시 실행
         * 2. 두개 작업 완료 시 구매 주문 생성 실행
         */
        // 재고 확인/예약 작업
        CompletableFuture<Void> inventorySequenceActivity = CompletableFuture.supplyAsync(
                new InventorySequenceActivity(order, new DirectingInventory())
        );

        // 결제 인증/승인 작업
        CompletableFuture<ApprovalOrderPayment> paymentGatewaySequenceActivity = CompletableFuture.supplyAsync(
                new PaymentGatewaySequenceActivity(order)
        );

        // 구매 주문 생성 작업
        BiFunction<Void, ApprovalOrderPayment, Void> purchaseOrderSequenceActivity =
                new PurchaseOrderSequenceActivity(order,  new DirectDeliveryPurchaseOrder(new DefaultPurchaseOrder()));

        inventorySequenceActivity.thenCombineAsync(paymentGatewaySequenceActivity, purchaseOrderSequenceActivity)
                .exceptionally(throwable -> {
                    // 주문 실패 이벤트 발행
                    if(throwable.getCause() instanceof StockOutException) {
                        PrettySystemOut.println(this.getClass(), "재고 없음 예외 발생");
                        EventPublisher.instance().publish(new OrderFailed(order.getOrderId(), "Stockout"));
                    }
                    return null;
                });
    }
}

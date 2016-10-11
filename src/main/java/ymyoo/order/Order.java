package ymyoo.order;

import ymyoo.order.event.OrderCompleteEvent;
import ymyoo.order.event.OrderCompleteEventListener;
import ymyoo.order.inventory.InventoryTask;
import ymyoo.order.paymentgateway.ApprovalOrderPayment;
import ymyoo.order.paymentgateway.PaymentGatewayTask;

import java.util.concurrent.CompletableFuture;

/**
 * Created by 유영모 on 2016-10-07.
 */
public class Order {
    private OrderItem orderItem;
    private OrderPayment orderPayment;

    public Order(OrderItem orderItem, OrderPayment orderPayment) {
        this.orderItem = orderItem;
        this.orderPayment = orderPayment;
    }

    public String placeOrder(OrderCompleteEventListener listener) {
        String orderId =  OrderIdGenerator.generate();
        System.out.println("[Current Thread ID - " + Thread.currentThread().getId() + "]" + "주문 아이디 생성 : " + orderId);

        // 비동기 작업
        // 1. 재고 확인/예약 작업
        CompletableFuture<Void> inventoryFuture = CompletableFuture.supplyAsync(new InventoryTask(this.orderItem));
        // 2. 결제 인증/승인 작업
        CompletableFuture<ApprovalOrderPayment> paymentGatewayFuture =
                CompletableFuture.supplyAsync(new PaymentGatewayTask(this.orderPayment));

        // 3. 재고 확인/예약 작업 및 결제 인증/승인 작업 완료 시 구매 주문 생성!!
        inventoryFuture.thenCombineAsync(paymentGatewayFuture, (Void, approvalOrderPayment) -> {
            // 구매 주문 생성
            PurchaseOrder.create(this, approvalOrderPayment);

            // 주문 완료 이벤트 발행
            listener.setOrderCompleted(new OrderCompleteEvent(orderId));

            System.out.println("[Current Thread ID - " + Thread.currentThread().getId() + "]" + "주문 완료....");
            return null;
        });

        return orderId;
    }
}

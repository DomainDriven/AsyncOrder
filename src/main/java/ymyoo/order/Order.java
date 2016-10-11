package ymyoo.order;

import ymyoo.order.event.OrderCompleted;
import ymyoo.order.event.OrderFailed;
import ymyoo.order.event.messaging.EventPublisher;
import ymyoo.order.inventory.InventoryTask;
import ymyoo.order.inventory.exception.StockOutException;
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

    public String placeOrder() {
        String orderId =  OrderIdGenerator.generate();
        System.out.println("[Current Thread ID - " + Thread.currentThread().getId() + "]" + "주문 아이디 생성 : " + orderId);

        /**
         * 비동기 작업 순서
         *
         * 1. 재고 확인/예약 작업과 결제 인증/승인 작업 동시 실행
         * 2. 두개 작업 완료 시 구매 주문 생성 실행
         */
        // 재고 확인/예약 작업
        CompletableFuture<Void> inventoryFuture = CompletableFuture.supplyAsync(new InventoryTask(this.orderItem));

        // 결제 인증/승인 작업
        CompletableFuture<ApprovalOrderPayment> paymentGatewayFuture =
                CompletableFuture.supplyAsync(new PaymentGatewayTask(this.orderPayment));

        // 재고 확인/예약 작업 및 결제 인증/승인 작업 완료 시 구매 주문 생성!!
        inventoryFuture.thenCombineAsync(paymentGatewayFuture, (Void, approvalOrderPayment) -> {
            // 구매 주문 생성
            PurchaseOrder.create(this, approvalOrderPayment);

            System.out.println("[Current Thread ID - " + Thread.currentThread().getId() + "]" + "주문 완료....");

            // 주문 완료 이벤트 발행
            EventPublisher.instance().publish(new OrderCompleted(orderId));

            return null;
        }).exceptionally(throwable -> {
            System.out.println("[Current Thread ID - " + Thread.currentThread().getId() + "]" +  "예외 발생!!!!");
            // 주문 실패 이벤트 발행
            if(throwable.getCause() instanceof StockOutException) {
                System.out.println("[Current Thread ID - " + Thread.currentThread().getId() + "]" + "재고 없음 예외");
                EventPublisher.instance().publish(new OrderFailed(orderId, "Stockout"));
            }
            return null;
        });

        return orderId;
    }
}

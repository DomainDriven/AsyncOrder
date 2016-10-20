package ymyoo.order;

import ymyoo.order.event.OrderFailed;
import ymyoo.messaging.EventPublisher;
import ymyoo.order.inventory.InventoryTransactionActivity;
import ymyoo.order.inventory.exception.StockOutException;
import ymyoo.order.payment.ApprovalOrderPayment;
import ymyoo.order.payment.PaymentGatewayTransactionActivity;
import ymyoo.order.purchase.PurchaseOrderTransactionActivity;
import ymyoo.util.PrettySystemOut;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

/**
 * Created by 유영모 on 2016-10-07.
 */
public class Order {
    private String orderId;

    private OrderItem orderItem;
    private OrderPayment orderPayment;

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public OrderPayment getOrderPayment() {
        return orderPayment;
    }

    public String getOrderId() {
        return orderId;
    }

    public Order(OrderItem orderItem, OrderPayment orderPayment) {
        this.orderItem = orderItem;
        this.orderPayment = orderPayment;
    }

    public String placeOrder() {
        this.orderId = OrderIdGenerator.generate();
        PrettySystemOut.println(this.getClass(), "주문 아이디 생성 : " + orderId);

        /**
         * 비동기 작업 순서
         *
         * 1. 재고 확인/예약 작업과 결제 인증/승인 작업 동시 실행
         * 2. 두개 작업 완료 시 구매 주문 생성 실행
         */
        // 재고 확인/예약 작업
        CompletableFuture<Void> inventoryTransactionActivity = CompletableFuture.supplyAsync(new InventoryTransactionActivity(this));

        // 결제 인증/승인 작업
        CompletableFuture<ApprovalOrderPayment> paymentGatewayTransactionActivity =
                CompletableFuture.supplyAsync(new PaymentGatewayTransactionActivity(this));

        // 구매 주문 생성 작업
        BiFunction<Void, ApprovalOrderPayment, Void> purchaseOrderTransactionActivity = new PurchaseOrderTransactionActivity(this);

        inventoryTransactionActivity.thenCombineAsync(paymentGatewayTransactionActivity, purchaseOrderTransactionActivity)
                .exceptionally(throwable -> {
                    // 주문 실패 이벤트 발행
                    if(throwable.getCause() instanceof StockOutException) {
                        PrettySystemOut.println(this.getClass(), "재고 없음 예외 발생");
                        EventPublisher.instance().publish(new OrderFailed(orderId, "Stockout"));
                    }
                    return null;
                });

        return orderId;
    }
}

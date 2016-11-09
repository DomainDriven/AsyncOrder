package ymyoo.domain.order;

import ymyoo.domain.inventory.exception.StockOutException;
import ymyoo.domain.order.event.OrderCompleted;
import ymyoo.domain.order.event.OrderFailed;
import ymyoo.domain.order.workflow.OrderProcessor;
import ymyoo.domain.order.workflow.OrderProcessorFactory;
import ymyoo.infra.messaging.EventPublisher;
import ymyoo.util.PrettySystemOut;

import java.util.concurrent.CompletableFuture;

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

        OrderProcessor orderProcessor = OrderProcessorFactory.create(this);

        CompletableFuture.runAsync(() -> orderProcessor.runWorkflow(this))
                .whenComplete((voidValue, error) -> {
                    if (error != null) {
                        // 주문 실패 이벤트
                        if(error.getCause() instanceof StockOutException) {
                            PrettySystemOut.println(this.getClass(), "재고 없음 예외 발생");
                            EventPublisher.instance().publish(new OrderFailed(this.getOrderId(), "Stockout"));
                        }
                        EventPublisher.instance().publish(new OrderFailed(this.getOrderId(), ""));
                    } else {
                        // 주문 성공 이벤트
                        EventPublisher.instance().publish(new OrderCompleted(this.getOrderId()));
                    }
                });

        return orderId;
    }
}

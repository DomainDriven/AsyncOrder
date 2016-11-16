package ymyoo.order.domain;

import ymyoo.order.domain.workflow.OrderProcessor;
import ymyoo.order.domain.workflow.OrderProcessorFactory;
import ymyoo.order.utility.PrettySystemOut;

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
        orderProcessor.runWorkflow(this);
        return orderId;
    }
}

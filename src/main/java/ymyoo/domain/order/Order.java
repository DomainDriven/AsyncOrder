package ymyoo.domain.order;

import ymyoo.domain.order.exception.UnSupportedDeliveryTypeException;
import ymyoo.domain.order.workflow.AgencyDeliveryProductProcessor;
import ymyoo.domain.order.workflow.DirectingDeliveryProductProcessor;
import ymyoo.domain.order.workflow.OrderProcess;
import ymyoo.util.PrettySystemOut;

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

        if(orderItem.getDeliveryType() == OrderItemDeliveryType.DIRECTING) {
            OrderProcess<Order> orderProcess = new DirectingDeliveryProductProcessor();
            orderProcess.runWorkflow(this);
        } else if(orderItem.getDeliveryType() == OrderItemDeliveryType.AGENCY) {
            OrderProcess<Order> orderProcess = new AgencyDeliveryProductProcessor();
            orderProcess.runWorkflow(this);
        } else {
            throw new UnSupportedDeliveryTypeException();
        }

        return orderId;
    }
}

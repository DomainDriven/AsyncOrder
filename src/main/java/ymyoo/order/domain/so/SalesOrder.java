package ymyoo.order.domain.so;

import ymyoo.order.domain.workflow.OrderProcessManager;
import ymyoo.order.domain.workflow.OrderProcessManagerFactory;
import ymyoo.utility.PrettySystemOut;

/**
 * Created by 유영모 on 2016-10-07.
 */
public class SalesOrder {
    private String orderId;

    private SalesOrderItem orderItem;
    private SalesOrderPayment orderPayment;

    public SalesOrderItem getOrderItem() {
        return orderItem;
    }

    public SalesOrderPayment getOrderPayment() {
        return orderPayment;
    }

    public String getOrderId() {
        return orderId;
    }

    public SalesOrder(SalesOrderItem orderItem, SalesOrderPayment orderPayment) {
        this.orderItem = orderItem;
        this.orderPayment = orderPayment;
    }

    public String placeOrder() {
        this.orderId = SalesOrderIdGenerator.generate();
        PrettySystemOut.println(this.getClass(), "주문 아이디 생성 : " + orderId);

        OrderProcessManager processManager = OrderProcessManagerFactory.create(this);
        processManager.runWorkflow(this);
        return orderId;
    }
}

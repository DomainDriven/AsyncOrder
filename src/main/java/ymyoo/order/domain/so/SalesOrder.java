package ymyoo.order.domain.so;

import ymyoo.order.domain.workflow.OrderProcessManager;
import ymyoo.order.domain.workflow.OrderProcessManagerFactory;
import ymyoo.utility.PrettySystemOut;

/**
 * Created by 유영모 on 2016-10-07.
 */
public class SalesOrder {
    private Orderer orderer;
    private String orderId;
    private SalesOrderItem orderItem;
    private SalesOrderPayment orderPayment;

    public SalesOrder(Orderer orderer, SalesOrderItem orderItem, SalesOrderPayment orderPayment) {
        this.orderer = orderer;
        this.orderItem = orderItem;
        this.orderPayment = orderPayment;
    }

    public Orderer getOrderer() {
        return orderer;
    }

    public String getOrderId() {
        return orderId;
    }

    public SalesOrderItem getOrderItem() {
        return orderItem;
    }

    public SalesOrderPayment getOrderPayment() {
        return orderPayment;
    }

    public String placeOrder() {
        this.orderId = SalesOrderIdGenerator.generate();
        PrettySystemOut.println(this.getClass(), "주문 아이디 생성 : " + orderId);

        OrderProcessManager processManager = OrderProcessManagerFactory.create(this);
        processManager.runWorkflow(this);
        return orderId;
    }
}

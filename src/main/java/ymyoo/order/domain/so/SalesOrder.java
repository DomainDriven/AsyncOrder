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

    private OrderProcessManager processManager;

    public SalesOrder(Orderer orderer, SalesOrderItem orderItem, SalesOrderPayment orderPayment) {
        this.orderer = orderer;
        this.orderItem = orderItem;
        this.orderPayment = orderPayment;
        this.orderId = SalesOrderIdGenerator.generate();
        this.processManager = OrderProcessManagerFactory.create(this);
    }

    public SalesOrder(Orderer orderer, SalesOrderItem orderItem, SalesOrderPayment orderPayment,
                      OrderProcessManager processManager) {
        this.orderer = orderer;
        this.orderItem = orderItem;
        this.orderPayment = orderPayment;
        this.orderId = SalesOrderIdGenerator.generate();
        this.processManager = processManager;
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
        processManager.runWorkflow(this);
        return orderId;
    }
}

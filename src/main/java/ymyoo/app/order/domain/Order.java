package ymyoo.app.order.domain;

import ymyoo.app.order.adapter.messaging.LogOrderStatusChannelAdapter;
import ymyoo.app.order.domain.workflow.OrderProcessManager;
import ymyoo.app.order.domain.workflow.OrderProcessManagerFactory;
import ymyoo.app.order.infrastructure.OrderEntityManagerFactory;
import ymyoo.app.order.infrastructure.repository.OrderStatusRepository;

/**
 * Created by 유영모 on 2016-10-07.
 */
public class Order {
    private String orderId;
    private Orderer orderer;
    private OrderItem orderItem;
    private OrderPayment orderPayment;

    private OrderProcessManager processManager;
    private LogOrderStatusChannelAdapter orderStatusChannelAdapter = new LogOrderStatusChannelAdapter();

    public Order(String orderId) {
        this.orderId = orderId;
    }

    public Order(Orderer orderer, OrderItem orderItem, OrderPayment orderPayment) {
        this.orderer = orderer;
        this.orderItem = orderItem;
        this.orderPayment = orderPayment;
        this.orderId = OrderIdGenerator.generate();
        this.processManager = OrderProcessManagerFactory.create(this);
    }

    public Order(Orderer orderer, OrderItem orderItem, OrderPayment orderPayment,
                 OrderProcessManager processManager) {
        this.orderer = orderer;
        this.orderItem = orderItem;
        this.orderPayment = orderPayment;
        this.orderId = OrderIdGenerator.generate();
        this.processManager = processManager;
    }

    public Orderer getOrderer() {
        return orderer;
    }

    public String getOrderId() {
        return orderId;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public OrderPayment getOrderPayment() {
        return orderPayment;
    }

    public String placeOrder() {
        processManager.runWorkflow(this);
        return orderId;
    }

    public OrderStatus getOrderStatus() {
        OrderStatusRepository orderStatusRepository = new OrderStatusRepository(OrderEntityManagerFactory.getEntityManagerFactory());
        return orderStatusRepository.find(this.getOrderId());
    }

    public void setOrderStatus(OrderStatus.Status status) {
        System.out.println("Order : " + status);
        orderStatusChannelAdapter.logStatus(new OrderStatus(orderId, status));
    }
}

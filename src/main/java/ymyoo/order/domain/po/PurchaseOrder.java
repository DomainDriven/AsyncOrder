package ymyoo.order.domain.po;

/**
 * Created by 유영모 on 2016-12-15.
 */
public class PurchaseOrder {
    private String orderId;
    private PurchaseOrderItem orderItem;
    private PurchaseOrderPayment orderPayment;

    public PurchaseOrder(String orderId, PurchaseOrderItem orderItem, PurchaseOrderPayment orderPayment) {
        this.orderId = orderId;
        this.orderItem = orderItem;
        this.orderPayment = orderPayment;
    }


    public String getOrderId() {
        return orderId;
    }

    public PurchaseOrderItem getOrderItem() {
        return orderItem;
    }

    public PurchaseOrderPayment getOrderPayment() {
        return orderPayment;
    }
}

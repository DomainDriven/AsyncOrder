package ymyoo.app.order.domain.command.po;

/**
 * Created by 유영모 on 2016-12-15.
 */
public class PurchaseOrder {
    private Purchaser purchaser;
    private String orderId;
    private PurchaseOrderItem orderItem;
    private PurchaseOrderPayment orderPayment;

    public PurchaseOrder(String orderId, Purchaser purchaser, PurchaseOrderItem orderItem, PurchaseOrderPayment orderPayment) {
        this.purchaser = purchaser;
        this.orderId = orderId;
        this.orderItem = orderItem;
        this.orderPayment = orderPayment;
    }

    public Purchaser getPurchaser() {
        return purchaser;
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

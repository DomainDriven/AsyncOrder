package ymyoo.app.order.domain.command.po;

import javax.persistence.*;

/**
 * Created by 유영모 on 2016-12-15.
 */
@Entity
public class PurchaseOrder {
    @Id
    private String orderId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "purchaserId")
    private Purchaser purchaser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "purchaseOrderItemId")
    private PurchaseOrderItem orderItem;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "purchaseOrderPaymentId")
    private PurchaseOrderPayment orderPayment;

    public PurchaseOrder() {
    }

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

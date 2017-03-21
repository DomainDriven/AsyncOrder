package ymyoo.app.order.domain.command.po;

import javax.persistence.*;

/**
 * Created by 유영모 on 2016-12-15.
 */
@Entity
@Table(name = "PURCHASE_ORDER")
public class PurchaseOrder {
    @Id
    @Column(name = "ORDER_ID")
    private String orderId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PURCHASER_ID")
    private Purchaser purchaser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PURCHASE_ORDER_ITEM_ID")
    private PurchaseOrderItem orderItem;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PURCHASE_ORDER_PAYMENT_ID")
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

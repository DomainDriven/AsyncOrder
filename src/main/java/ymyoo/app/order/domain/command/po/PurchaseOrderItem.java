package ymyoo.app.order.domain.command.po;

import ymyoo.app.order.domain.command.OrderItemDeliveryType;

import javax.persistence.*;

/**
 * Created by 유영모 on 2016-12-15.
 */
@Entity
@Table(name = "PURCHASE_ORDER_ITEM")
public class PurchaseOrderItem {

    @Id @GeneratedValue
    @Column(name = "PURCHASE_ORDER_ITEM_ID")
    private Long id;

    /** 상품 번호 **/
    @Column(name = "PRODUCT_ID")
    private String productId;

    /** 주문 수량 **/
    @Column(name = "ORDER_QTY")
    private int orderQty;

    /** 배송 유형 **/
    private OrderItemDeliveryType deliveryType;

    public PurchaseOrderItem() {
    }

    public PurchaseOrderItem(String productId, int orderQty, OrderItemDeliveryType deliveryType) {
        this.productId = productId;
        this.orderQty = orderQty;
        this.deliveryType = deliveryType;
    }

    public Long getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public OrderItemDeliveryType getDeliveryType() {
        return deliveryType;
    }
}
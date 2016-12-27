package ymyoo.app.order.domain.po;

import ymyoo.app.order.domain.so.OrderItemDeliveryType;

/**
 * Created by 유영모 on 2016-12-15.
 */
public class PurchaseOrderItem {
    /** 상품 번호 **/
    private String productId;

    /** 주문 수량 **/
    private int orderQty;

    /** 배송 유형 **/
    private OrderItemDeliveryType deliveryType;

    public PurchaseOrderItem(String productId, int orderQty, OrderItemDeliveryType deliveryType) {
        this.productId = productId;
        this.orderQty = orderQty;
        this.deliveryType = deliveryType;
    }
}

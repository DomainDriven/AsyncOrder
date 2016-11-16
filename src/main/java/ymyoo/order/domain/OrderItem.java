package ymyoo.order.domain;

/**
 * 주문 아이템
 * 사용자가 주문한 정보는 주문 처리 과정에서 변하지 않아야 하므로 Immutable 클래스로..
 * Created by 유영모 on 2016-10-07.
 */
public class OrderItem {

    /** 상품 번호 **/
    private String productId;

    /** 주문 수량 **/
    private int orderQty;

    /** 배송 유형 **/
    private OrderItemDeliveryType deliveryType;

    public OrderItem(String productId, int orderQty, OrderItemDeliveryType deliveryType) {
        this.productId = productId;
        this.orderQty = orderQty;
        this.deliveryType = deliveryType;
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

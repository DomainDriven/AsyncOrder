package ymyoo.app.inventory.domain;

/**
 * Created by 유영모 on 2016-11-25.
 */
public class TakingOrderItem {
    public enum DeliveryType {
        /** 자사 배송(a.k.a 직배) */
        DIRECTING,
        /** 배송 대행(a.k.a 제 3자 배송) */
        AGENCY
    }

    private DeliveryType deliveryType;
    private String productId;
    private int orderQty;

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }
}

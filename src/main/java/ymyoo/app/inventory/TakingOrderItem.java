package ymyoo.app.inventory;

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


    public TakingOrderItem(DeliveryType deliveryType, String productId, int orderQty) {
        this.deliveryType = deliveryType;
        this.productId = productId;
        this.orderQty = orderQty;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public String getProductId() {
        return productId;
    }

    public int getOrderQty() {
        return orderQty;
    }
}

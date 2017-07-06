package ymyoo.app.order.domain.query.dto;

/**
 * Created by yooyoung-mo on 2017. 7. 4..
 */
public enum PlacingOrderStatus {
    INVENTORY_REQUEST,
    INVENTORY_CHECKED,
    PAYMENT_REQUEST,
    PAYMENT_DONE,
    PURCHASE_ORDER_REQUEST,
    PURCHASE_ORDER_CREATED,
    ORDER_FAILED,
    ORDER_READY
}

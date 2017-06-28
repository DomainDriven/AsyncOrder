package ymyoo.app.order.domain.command;

/**
 * Created by yooyoung-mo on 2017. 6. 23..
 */
public enum OrderStatus {
  INVENTORY_CHECKED_REQUEST,
  INVENTORY_CHECKED,
  PAYMENT_REQUEST,
  PAYMENT_DONE,
  PURCHASE_ORDER_CREATED,
  ORDER_FAILED,
  ORDER_READY
}

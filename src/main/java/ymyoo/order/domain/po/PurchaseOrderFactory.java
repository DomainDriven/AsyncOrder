package ymyoo.order.domain.po;

/**
 * Created by 유영모 on 2016-12-15.
 */
public class PurchaseOrderFactory {
    public static PurchaseOrder create(String orderId, Purchaser purchaser, PurchaseOrderItem orderItem, PurchaseOrderPayment orderPayment) {
        return new PurchaseOrder(orderId, purchaser, orderItem, orderPayment);
    }
}

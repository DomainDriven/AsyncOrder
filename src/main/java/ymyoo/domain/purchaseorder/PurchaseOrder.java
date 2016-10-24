package ymyoo.domain.purchaseorder;

import ymyoo.domain.order.Order;
import ymyoo.domain.payment.ApprovalOrderPayment;

/**
 * 구매 주문 인터페이스
 * Created by 유영모 on 2016-10-19.
 */
public interface PurchaseOrder {
    void create(Order order, ApprovalOrderPayment approvalOrderPayment);
}

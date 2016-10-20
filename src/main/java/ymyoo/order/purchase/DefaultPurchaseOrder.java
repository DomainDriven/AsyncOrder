package ymyoo.order.purchase;

import ymyoo.order.Order;
import ymyoo.order.payment.ApprovalOrderPayment;
import ymyoo.util.PrettySystemOut;

/**
 * 구매 주문
 * Created by 유영모 on 2016-10-07.
 */
public class DefaultPurchaseOrder implements  PurchaseOrder {

    @Override
    public void create(Order order, ApprovalOrderPayment approvalOrderPayment) {
        PrettySystemOut.println(DefaultPurchaseOrder.class, "구매 주문 생성....");
    }
}

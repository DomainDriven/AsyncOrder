package ymyoo.order;

import ymyoo.order.paymentgateway.ApprovalOrderPayment;
import ymyoo.util.PrettySystemOut;

/**
 * 구매 주문
 * Created by 유영모 on 2016-10-07.
 */
public class PurchaseOrder {

    public static void create(Order order, ApprovalOrderPayment approvalOrderPayment) {
        PrettySystemOut.println(PurchaseOrder.class, "구매 주문 생성....");
    }
}

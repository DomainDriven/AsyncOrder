package ymyoo.order.domain.po.impl;

import ymyoo.order.domain.Order;
import ymyoo.payment.ApprovalOrderPayment;
import ymyoo.order.domain.po.PurchaseOrder;
import ymyoo.order.util.PrettySystemOut;

/**
 * 구매 주문
 * Created by 유영모 on 2016-10-07.
 */
public class DefaultPurchaseOrder implements PurchaseOrder {

    @Override
    public void create(Order order, ApprovalOrderPayment approvalOrderPayment) {
        PrettySystemOut.println(DefaultPurchaseOrder.class, "구매 주문 생성....");
    }
}

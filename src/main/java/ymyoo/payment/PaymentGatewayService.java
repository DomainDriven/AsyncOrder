package ymyoo.payment;

import ymyoo.order.domain.OrderPayment;

/**
 * Created by 유영모 on 2016-11-17.
 */
public class PaymentGatewayService {
    public ApprovalOrderPayment authenticateAndApproval(OrderPayment orderPayment) {
        PaymentGateway paymentGateway = new PaymentGateway();
        paymentGateway.authenticate(orderPayment);
        return paymentGateway.approve(orderPayment);
    }
}

package ymyoo.payment;

/**
 * Created by 유영모 on 2016-11-17.
 */
public class PaymentGatewayService {
    public ApprovalPayment authenticateAndApproval(TakingOrderPayment orderPayment) {
        PaymentGateway paymentGateway = new PaymentGateway();
        paymentGateway.authenticate(orderPayment);
        return paymentGateway.approve(orderPayment);
    }
}

package ymyoo.order.paymentgateway;

import ymyoo.order.OrderPayment;
import ymyoo.order.paymentgateway.ApprovalOrderPayment;
import ymyoo.order.paymentgateway.PaymentGateway;

import java.util.function.Supplier;

/**
 * Created by 유영모 on 2016-10-10.
 */
public class PaymentGatewayTask implements Supplier<ApprovalOrderPayment> {
    OrderPayment orderPayment;

    public PaymentGatewayTask(OrderPayment orderPayment) {
        this.orderPayment = orderPayment;
    }

    @Override
    public ApprovalOrderPayment get() {
        System.out.println("결제 관련 작업 시작...");

        PaymentGateway paymentGateway = new PaymentGateway();
        paymentGateway.authenticate(this.orderPayment);
        ApprovalOrderPayment approvalOrderPayment = paymentGateway.approve(this.orderPayment);

        System.out.println("결제 관련 작업 끝...");

        return approvalOrderPayment;
    }
}

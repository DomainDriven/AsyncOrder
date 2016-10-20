package ymyoo.order.payment;

import ymyoo.order.Order;

import java.util.function.Supplier;

/**
 * 주문시 수행 해야할 결제 관련 작업 모음
 *
 * {@link java.util.concurrent.CompletableFuture}를 사용하여 비동기 처리 하기 위해
 * {@link java.util.function.Supplier}를 구현함.
 *
 * Created by 유영모 on 2016-10-10.
 */
public class PaymentGatewayTransactionActivity implements Supplier<ApprovalOrderPayment> {
    private Order order;

    public PaymentGatewayTransactionActivity(Order order) {
        this.order = order;
    }

    @Override
    public ApprovalOrderPayment get() {
        PaymentGateway paymentGateway = new PaymentGateway();
        paymentGateway.authenticate(this.order.getOrderPayment());
        ApprovalOrderPayment approvalOrderPayment = paymentGateway.approve(this.order.getOrderPayment());

        return approvalOrderPayment;
    }
}

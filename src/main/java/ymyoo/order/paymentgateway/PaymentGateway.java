package ymyoo.order.paymentgateway;

import ymyoo.order.OrderPayment;

/**
 * 결제
 *
 * Created by 유영모 on 2016-10-07.
 */
public class PaymentGateway {
    /**
     * 인증
     */
    public void authenticate(OrderPayment orderPayment) {
        try { Thread.sleep(250); } catch (InterruptedException e) {}
        System.out.println("[Current Thread ID - " + Thread.currentThread().getId() + "][PaymentGateway Task]-결제 인증-" + "신용 카드 " + orderPayment.getCreditCardNo());
    }

    /**
     * 승인
     */
    public ApprovalOrderPayment approve(OrderPayment orderPayment) {
        try { Thread.sleep(250); } catch (InterruptedException e) {}
        System.out.println("[Current Thread ID - " + Thread.currentThread().getId() + "][PaymentGateway Task]-결제 승인-" + "결제 금액 " + orderPayment.getOrderAmount());
        return new ApprovalOrderPayment("tid:10232");
    }

}
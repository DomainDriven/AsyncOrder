package ymyoo.app.payment.adapter.messaging;

import com.google.gson.Gson;
import ymyoo.app.payment.domain.ApprovalPayment;
import ymyoo.app.payment.domain.PaymentGatewayService;
import ymyoo.app.payment.domain.TakingOrderPayment;
import ymyoo.messaging.Message;

/**
 * Created by 유영모 on 2017-01-02.
 */
public class PaymentChannelAdapter {

    public ApprovalPayment authenticateAndApproval(Message message) {
        // 메시지 변환
        TakingOrderPayment takingOrderPayment = PaymentChannelAdapter.AuthApvPaymentMessageTranslator.translate(message.getBody());

        // 서비스 호출
        PaymentGatewayService paymentGatewayService = new PaymentGatewayService();
        return paymentGatewayService.authenticateAndApproval(takingOrderPayment);
    }

    static class AuthApvPaymentMessageTranslator {
        public static TakingOrderPayment translate(String data) {
            return new Gson().fromJson(data, TakingOrderPayment.class);
        }
    }


}

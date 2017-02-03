package ymyoo.app.payment.adapter.messaging;

import org.apache.commons.beanutils.BeanUtils;
import ymyoo.app.payment.domain.ApprovalPayment;
import ymyoo.app.payment.domain.PaymentGatewayService;
import ymyoo.app.payment.domain.TakingOrderPayment;
import ymyoo.messaging.core.Message;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by 유영모 on 2017-01-02.
 */
public class PaymentChannelAdapter {

    public ApprovalPayment authenticateAndApproval(Message message) {
        // 메시지 변환
        TakingOrderPayment takingOrderPayment = null;
        try {
            takingOrderPayment = AuthApvPaymentMessageTranslator.translate(message.getBody());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // 서비스 호출
        PaymentGatewayService paymentGatewayService = new PaymentGatewayService();
        return paymentGatewayService.authenticateAndApproval(takingOrderPayment);
    }

    static class AuthApvPaymentMessageTranslator {
        public static TakingOrderPayment translate(Object data) throws InvocationTargetException, IllegalAccessException {
            TakingOrderPayment takingOrderPayment = new TakingOrderPayment();
            BeanUtils.populate(takingOrderPayment, (Map)data);
            return takingOrderPayment;
        }
    }
}

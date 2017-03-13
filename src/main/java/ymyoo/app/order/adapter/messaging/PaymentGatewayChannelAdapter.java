package ymyoo.app.order.adapter.messaging;

import org.apache.commons.beanutils.BeanUtils;
import ymyoo.app.order.domain.command.OrderPayment;
import ymyoo.app.order.domain.command.po.ApprovalOrderPayment;
import ymyoo.messaging.core.MessageChannels;
import ymyoo.messaging.core.Message;
import ymyoo.messaging.core.Requester;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 결제 채널 어뎁터
 *
 * Created by 유영모 on 2016-11-17.
 */
public class PaymentGatewayChannelAdapter {
    public ApprovalOrderPayment authenticateAndApproval(final String orderId, final OrderPayment orderPayment) {
        // 메시지 생성
        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("orderId", orderId);
        messageBody.put("creditCardNo", orderPayment.getCreditCardNo());
        messageBody.put("orderAmount", String.valueOf(orderPayment.getOrderAmount()));

        // 메시지 발신
        Requester requester = new Requester(MessageChannels.PAYMENT_AUTH_APP_REQUEST, MessageChannels.PAYMENT_AUTH_APP_REPLY);
        requester.send(messageBody);

        // 메시지 수신
        Message receivedMessage = requester.receive();
        try {
            ApprovalOrderPayment approvalOrderPayment = MessageTranslator.translate(receivedMessage);
            return approvalOrderPayment;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    static class MessageTranslator {
        public static ApprovalOrderPayment translate(Message message) throws InvocationTargetException, IllegalAccessException {
            ApprovalOrderPayment approvalOrderPayment = new ApprovalOrderPayment();
            BeanUtils.populate(approvalOrderPayment, ((Map)message.getBody()));

            return approvalOrderPayment;
        }
    }
}

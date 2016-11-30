package ymyoo.order.message.endpoint;

import ymyoo.infra.messaging.remote.channel.Requester;
import ymyoo.infra.messaging.remote.channel.message.Message;
import ymyoo.infra.messaging.remote.channel.message.MessageHead;
import ymyoo.order.domain.ApprovalOrderPayment;
import ymyoo.order.domain.OrderPayment;

import java.util.HashMap;
import java.util.Map;

/**
 * 결제 채널 어뎁터
 *
 * Created by 유영모 on 2016-11-17.
 */
public class PaymentGatewayChannelAdapter {
    private Requester requester = new Requester();

    public ApprovalOrderPayment authenticateAndApproval(String orderId, OrderPayment orderPayment) {
        // 메시지 발신
        MessageHead messageHead = new MessageHead(orderId, MessageHead.MessageType.AUTH_APV_PAYMENT);
        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("creditCardNo", orderPayment.getCreditCardNo());
        messageBody.put("orderAmount", String.valueOf(orderPayment.getOrderAmount()));
        requester.send(new Message(messageHead, messageBody));

        // 메시지 수신
        Message receivedMessage = requester.receive(orderId);
        return MessageTranslator.translate(receivedMessage);
    }

    static class MessageTranslator {
        public static ApprovalOrderPayment translate(Message message) {
            String tid = message.getBody().get("tid");
            return new ApprovalOrderPayment(tid);
        }
    }
}

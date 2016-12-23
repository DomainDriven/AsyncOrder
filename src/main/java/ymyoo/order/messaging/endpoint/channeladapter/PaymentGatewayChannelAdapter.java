package ymyoo.order.messaging.endpoint.channeladapter;

import com.google.gson.Gson;
import ymyoo.infra.messaging.remote.channel.MessageChannel;
import ymyoo.order.domain.po.ApprovalOrderPayment;
import ymyoo.order.domain.so.SalesOrderPayment;
import ymyoo.order.messaging.endpoint.request.Requester;

import java.util.HashMap;
import java.util.Map;

/**
 * 결제 채널 어뎁터
 *
 * Created by 유영모 on 2016-11-17.
 */
public class PaymentGatewayChannelAdapter {
    public ApprovalOrderPayment authenticateAndApproval(SalesOrderPayment orderPayment) {
        // 메시지 생성
        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("creditCardNo", orderPayment.getCreditCardNo());
        messageBody.put("orderAmount", String.valueOf(orderPayment.getOrderAmount()));

        // 메시지 발신
        String correlationId =  java.util.UUID.randomUUID().toString().toUpperCase();
        Requester requester = new Requester(MessageChannel.PAYMENT_AUTH_APP_REQUEST, MessageChannel.PAYMENT_AUTH_APP_REPLY, correlationId);
        requester.send(new Gson().toJson(messageBody));

        // 메시지 수신
        String receivedMessage = requester.receive();
        return MessageTranslator.translate(receivedMessage);
    }

    static class MessageTranslator {
        public static ApprovalOrderPayment translate(String message) {
            return new Gson().fromJson(message, ApprovalOrderPayment.class);
        }
    }
}

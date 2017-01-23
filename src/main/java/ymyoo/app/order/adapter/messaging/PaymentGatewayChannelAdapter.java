package ymyoo.app.order.adapter.messaging;

import com.google.gson.Gson;
import ymyoo.app.order.domain.OrderPayment;
import ymyoo.messaging.core.Callback;
import ymyoo.messaging.core.CallbackMessageConsumer;
import ymyoo.messaging.core.MessageChannels;
import ymyoo.messaging.core.Requester;

import java.util.HashMap;
import java.util.Map;

/**
 * 결제 채널 어뎁터
 *
 * Created by 유영모 on 2016-11-17.
 */
public class PaymentGatewayChannelAdapter {
    public void authenticateAndApproval(String correlationId, final String orderId, final OrderPayment orderPayment, Callback callback) {
        // 메시지 생성
        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("orderId", orderId);
        messageBody.put("creditCardNo", orderPayment.getCreditCardNo());
        messageBody.put("orderAmount", String.valueOf(orderPayment.getOrderAmount()));

        // 메시지 발신
        Requester requester = new Requester(MessageChannels.PAYMENT_AUTH_APP_REQUEST, MessageChannels.PAYMENT_AUTH_APP_REPLY, correlationId);
        requester.send(new Gson().toJson(messageBody));

        // 메시지 처리 후 응답 콜백 등록
        CallbackMessageConsumer.registerCallback(callback);
    }

}

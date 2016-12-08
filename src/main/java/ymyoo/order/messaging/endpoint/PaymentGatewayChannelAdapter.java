package ymyoo.order.messaging.endpoint;

import com.google.gson.Gson;
import ymyoo.infra.messaging.remote.channel.Callback;
import ymyoo.infra.messaging.remote.channel.MessageChannel;
import ymyoo.infra.messaging.remote.channel.MessageConsumer;
import ymyoo.infra.messaging.remote.channel.MessageProducer;
import ymyoo.order.domain.OrderPayment;

import java.util.HashMap;
import java.util.Map;

/**
 * 결제 채널 어뎁터
 *
 * Created by 유영모 on 2016-11-17.
 */
public class PaymentGatewayChannelAdapter {
    public void authenticateAndApproval(String id, OrderPayment orderPayment, Callback callback) {
        // 메시지 생성
        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("creditCardNo", orderPayment.getCreditCardNo());
        messageBody.put("orderAmount", String.valueOf(orderPayment.getOrderAmount()));

        // 메시지 발신
        MessageProducer producer = new MessageProducer(MessageChannel.PAYMENT_AUTH_APP_REQUEST, MessageChannel.PAYMENT_AUTH_APP_REPLY);
        producer.send(id, new Gson().toJson(messageBody));

        // 메시지 처리 후 응답 콜백 등록
        MessageConsumer.registerCallback(callback);
    }
}

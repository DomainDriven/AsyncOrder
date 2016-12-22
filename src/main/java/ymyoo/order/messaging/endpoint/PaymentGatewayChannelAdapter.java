package ymyoo.order.messaging.endpoint;

import com.google.gson.Gson;
import ymyoo.infra.messaging.remote.channel.Callback;
import ymyoo.infra.messaging.remote.channel.MessageChannel;
import ymyoo.infra.messaging.remote.channel.MessageConsumer;
import ymyoo.infra.messaging.remote.channel.MessageProducer;
import ymyoo.order.domain.po.ApprovalOrderPayment;
import ymyoo.order.domain.so.SalesOrderPayment;

import java.util.HashMap;
import java.util.Map;

/**
 * 결제 채널 어뎁터
 *
 * Created by 유영모 on 2016-11-17.
 */
public class PaymentGatewayChannelAdapter implements  Callback<ApprovalOrderPayment> {
    private final String id = java.util.UUID.randomUUID().toString().toUpperCase();
    private ApprovalOrderPayment approvalOrderPayment;

    public synchronized ApprovalOrderPayment authenticateAndApproval(SalesOrderPayment orderPayment) {
        // 메시지 생성
        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("creditCardNo", orderPayment.getCreditCardNo());
        messageBody.put("orderAmount", String.valueOf(orderPayment.getOrderAmount()));

        // 메시지 발신
        MessageProducer producer = new MessageProducer(MessageChannel.PAYMENT_AUTH_APP_REQUEST, MessageChannel.PAYMENT_AUTH_APP_REPLY);
        producer.send(id, new Gson().toJson(messageBody));

        // 메시지 처리 후 응답 콜백 등록
        MessageConsumer.registerCallback(this);

        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return approvalOrderPayment;

    }

    @Override
    public synchronized void call(ApprovalOrderPayment result) {
        this.approvalOrderPayment = result;
        this.notify();
    }

    @Override
    public ApprovalOrderPayment translate(String data) {
        return new Gson().fromJson(data, ApprovalOrderPayment.class);
    }

    @Override
    public String getId() {
        return this.id;
    }
}

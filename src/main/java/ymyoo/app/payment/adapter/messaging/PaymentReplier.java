package ymyoo.app.payment.adapter.messaging;

import ymyoo.app.payment.domain.ApprovalPayment;
import ymyoo.messaging.core.AbstractReplier;
import ymyoo.messaging.core.MessageChannels;
import ymyoo.messaging.core.Message;

/**
 * Created by 유영모 on 2017-01-02.
 */
public class PaymentReplier extends AbstractReplier {
    public PaymentReplier() {
        super(MessageChannels.PAYMENT_AUTH_APP_REQUEST);
    }

    @Override
    public void onMessage(Message receivedMessage) {
        PaymentChannelAdapter channelAdapter = new PaymentChannelAdapter();
        ApprovalPayment approvalPayment = channelAdapter.authenticateAndApproval(receivedMessage);

        // 결과를 메시지로 전송
        final String channel = receivedMessage.getHeaders().get("replyChannel");
        final String correlationId = receivedMessage.getMessageId();
        sendMessage(channel, correlationId, approvalPayment);
    }
}

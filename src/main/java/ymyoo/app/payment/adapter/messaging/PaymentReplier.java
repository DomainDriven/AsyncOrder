package ymyoo.app.payment.adapter.messaging;

import com.google.gson.Gson;
import ymyoo.app.payment.domain.ApprovalPayment;
import ymyoo.messaging.core.AbstractReplier;
import ymyoo.messaging.core.Message;
import ymyoo.messaging.core.MessageChannels;
import ymyoo.messaging.core.MessageProducer;

/**
 * Created by 유영모 on 2017-01-02.
 */
public class PaymentReplier extends AbstractReplier {
    public PaymentReplier() {
        super(MessageChannels.PAYMENT_AUTH_APP_REQUEST);
    }

    @Override
    public void onMessage(String replyChannel, Message message) {
        PaymentChannelAdapter channelAdapter = new PaymentChannelAdapter();
        ApprovalPayment approvalPayment = channelAdapter.authenticateAndApproval(message);

        // 결과를 메시지로 전송
        MessageProducer producer = new MessageProducer(replyChannel);
        producer.send(message.getId(), new Gson().toJson(approvalPayment));
    }
}

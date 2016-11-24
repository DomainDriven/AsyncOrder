package ymyoo.order.adapter;

import ymyoo.infra.messaging.remote.queue.ReplyMessage;
import ymyoo.infra.messaging.remote.queue.RequestMessage;
import ymyoo.infra.messaging.remote.queue.Requester;
import ymyoo.order.domain.OrderPayment;
import ymyoo.payment.ApprovalOrderPayment;

/**
 * Created by 유영모 on 2016-11-17.
 */
public class PaymentGatewayChannelAdapter {
    private Requester requester = new Requester();

    public void sendAuthenticatAndApproval(String orderId, OrderPayment orderPayment) {
        RequestMessage requestMessage = new RequestMessage(orderId, orderPayment, RequestMessage.MessageType.AUTH_APV_PAYMENT);
        requester.send(requestMessage);
    }

    public ReplyMessage listen(String orderId) {
        return requester.receive(orderId);
    }
}

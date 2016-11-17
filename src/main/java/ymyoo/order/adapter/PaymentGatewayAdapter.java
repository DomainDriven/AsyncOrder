package ymyoo.order.adapter;

import ymyoo.infra.messaging.remote.queue.ReplyMessage;
import ymyoo.infra.messaging.remote.queue.RequestMessage;
import ymyoo.infra.messaging.remote.queue.Requester;
import ymyoo.order.domain.OrderPayment;
import ymyoo.payment.ApprovalOrderPayment;

/**
 * Created by 유영모 on 2016-11-17.
 */
public class PaymentGatewayAdapter {

    public ApprovalOrderPayment authenticateAndApproval(String orderId, OrderPayment orderPayment) {
        RequestMessage requestMessage = new RequestMessage(orderId, orderPayment, RequestMessage.MessageType.AUTH_APV_PAYMENT);

        Requester requester = new Requester();
        requester.send(requestMessage);
        ReplyMessage replyMessage = requester.receive(orderId);

        if(replyMessage.getStatus() == ReplyMessage.ReplyMessageStatus.FAILURE) {
            throw new RuntimeException("결제 오류");
        } else {
            return (ApprovalOrderPayment)replyMessage.getBody();
        }
    }
}

package ymyoo.order.domain.workflow.activity.impl;

import ymyoo.infra.messaging.remote.queue.ReplyMessage;
import ymyoo.order.adapter.PaymentGatewayChannelAdapter;
import ymyoo.order.domain.Order;
import ymyoo.order.domain.workflow.activity.AsyncActivity;
import ymyoo.payment.ApprovalOrderPayment;

/**
 * 주문시 수행 해야할 결제 관련 작업 모음
 *
 * Created by 유영모 on 2016-10-10.
 */
public class PaymentGatewayAsyncActivity implements AsyncActivity<ReplyMessage, ApprovalOrderPayment> {
    private Order order;

    public PaymentGatewayAsyncActivity(Order order) {
        this.order = order;
    }

    @Override
    public void perform() {
        PaymentGatewayChannelAdapter channelAdapter = new PaymentGatewayChannelAdapter();
        channelAdapter.sendAuthenticatAndApproval(order.getOrderId(), order.getOrderPayment());
    }

    @Override
    public ApprovalOrderPayment callback(ReplyMessage replyMessage) {
        if(replyMessage.getStatus() == ReplyMessage.ReplyMessageStatus.FAILURE) {
            throw new RuntimeException("결제 오류");
        } else {
            return (ApprovalOrderPayment)replyMessage.getBody();
        }
    }
}

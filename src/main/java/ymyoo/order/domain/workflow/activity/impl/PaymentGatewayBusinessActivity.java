package ymyoo.order.domain.workflow.activity.impl;

import ymyoo.order.domain.ApprovalOrderPayment;
import ymyoo.order.domain.Order;
import ymyoo.order.domain.workflow.activity.BusinessActivity;
import ymyoo.order.messaging.endpoint.PaymentGatewayChannelAdapter;

/**
 * 주문시 수행 해야할 결제 관련 작업 모음
 *
 * Created by 유영모 on 2016-10-10.
 */
public class PaymentGatewayBusinessActivity implements BusinessActivity<Order, ApprovalOrderPayment> {
    private PaymentGatewayChannelAdapter channelAdapter = new PaymentGatewayChannelAdapter();

    @Override
    public ApprovalOrderPayment perform(Order order) {
        return channelAdapter.authenticateAndApproval(order.getOrderId(), order.getOrderPayment());
    }
}

package ymyoo.app.order.domain.command.workflow.activity.impl;

import ymyoo.app.order.adapter.messaging.PaymentGatewayChannelAdapter;
import ymyoo.app.order.domain.command.Order;
import ymyoo.app.order.domain.command.OrderStatus;
import ymyoo.app.order.domain.command.po.ApprovalOrderPayment;

/**
 * 주문시 수행 해야할 결제 관련 작업 모음
 *
 * Created by 유영모 on 2016-10-10.
 */
public class PaymentGatewayBusinessActivity extends AbstractBusinessActivity<Order, ApprovalOrderPayment> {

    public PaymentGatewayBusinessActivity(String orderId) {
        super(orderId, OrderStatus.Status.PAYMENT_REQUEST, OrderStatus.Status.PAYMENT_DONE);
    }


    @Override
    public ApprovalOrderPayment performActivity(Order order) {
        PaymentGatewayChannelAdapter channelAdapter = new PaymentGatewayChannelAdapter();
        return channelAdapter.authenticateAndApproval(order.getOrderId(), order.getOrderPayment());
    }
}

package ymyoo.app.order.domain.workflow.activity.impl;

import ymyoo.app.order.adapter.messaging.PaymentGatewayChannelAdapter;
import ymyoo.app.order.domain.Order;
import ymyoo.app.order.domain.po.ApprovalOrderPayment;
import ymyoo.app.order.domain.workflow.activity.AsyncBusinessActivity;
import ymyoo.messaging.core.Callback;

/**
 * 주문시 수행 해야할 결제 관련 작업 모음
 *
 * Created by 유영모 on 2016-10-10.
 */
public class PaymentGatewayBusinessActivity implements AsyncBusinessActivity<Order, ApprovalOrderPayment> {
    private final String id = java.util.UUID.randomUUID().toString().toUpperCase();

//    @Override
//    public ApprovalOrderPayment perform(Order order) {
//        PaymentGatewayChannelAdapter channelAdapter = new PaymentGatewayChannelAdapter();
//        return channelAdapter.authenticateAndApproval(order.getOrderId(), order.getOrderPayment());
//    }


    @Override
    public void perform(Order order, Callback<ApprovalOrderPayment> callback) {
        PaymentGatewayChannelAdapter channelAdapter = new PaymentGatewayChannelAdapter();
        channelAdapter.authenticateAndApproval(id, order.getOrderId(), order.getOrderPayment(), callback);
    }

    @Override
    public String getId() {
        return id;
    }
}

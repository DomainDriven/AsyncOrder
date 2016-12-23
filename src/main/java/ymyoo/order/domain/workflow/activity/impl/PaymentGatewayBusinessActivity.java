package ymyoo.order.domain.workflow.activity.impl;

import ymyoo.order.domain.po.ApprovalOrderPayment;
import ymyoo.order.domain.so.SalesOrder;
import ymyoo.order.domain.workflow.activity.BusinessActivity;
import ymyoo.order.messaging.endpoint.channeladapter.PaymentGatewayChannelAdapter;

/**
 * 주문시 수행 해야할 결제 관련 작업 모음
 *
 * Created by 유영모 on 2016-10-10.
 */
public class PaymentGatewayBusinessActivity implements BusinessActivity<SalesOrder, ApprovalOrderPayment> {

    @Override
    public ApprovalOrderPayment perform(SalesOrder salesOrder) {
        PaymentGatewayChannelAdapter channelAdapter = new PaymentGatewayChannelAdapter();
        return channelAdapter.authenticateAndApproval(salesOrder.getOrderPayment());
    }
}

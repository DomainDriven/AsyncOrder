package ymyoo.app.order.domain.workflow.activity.impl;

import ymyoo.app.order.domain.po.*;
import ymyoo.app.order.domain.so.SalesOrder;
import ymyoo.app.order.domain.workflow.activity.BusinessActivity;
import ymyoo.app.order.adapter.messaging.PurchaseOrderMessagingAdapter;
import ymyoo.utility.PrettySystemOut;

/**
 * Created by 유영모 on 2016-10-20.
 */
public class PurchaseOrderBusinessActivity implements BusinessActivity<ApprovalOrderPayment, Void> {
    private SalesOrder salesOrder;

    public PurchaseOrderBusinessActivity(SalesOrder salesOrder) {
        this.salesOrder = salesOrder;
    }

    @Override
    public Void perform(ApprovalOrderPayment approvalOrderPayment) {
        // 구매 주문 생성
        PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem(salesOrder.getOrderItem().getProductId(), salesOrder.getOrderItem().getOrderQty(), salesOrder.getOrderItem().getDeliveryType());
        PurchaseOrderPayment purchaseOrderPayment = new PurchaseOrderPayment(approvalOrderPayment.getTid());
        Purchaser purchaser = new Purchaser(salesOrder.getOrderer().getName(), salesOrder.getOrderer().getContactNumber());

        PurchaseOrder purchaseOrder = PurchaseOrderFactory.create(salesOrder.getOrderId(),purchaser, purchaseOrderItem, purchaseOrderPayment);

        PrettySystemOut.println(salesOrder.getClass(), "주문 완료....");

        // 구매 주문 생성 이벤트 발행
        PurchaseOrderMessagingAdapter channelAdapter = new PurchaseOrderMessagingAdapter();
        channelAdapter.onPurchaseOrderCreated(purchaseOrder);
        return null;
    }


}

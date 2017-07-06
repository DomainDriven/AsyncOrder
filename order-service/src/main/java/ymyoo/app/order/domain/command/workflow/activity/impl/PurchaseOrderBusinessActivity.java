package ymyoo.app.order.domain.command.workflow.activity.impl;

import ymyoo.app.order.adapter.messaging.PurchaseOrderChannelAdapter;
import ymyoo.app.order.domain.command.Order;
import ymyoo.app.order.domain.command.OrderStatus;
import ymyoo.app.order.domain.command.po.*;
import ymyoo.app.order.infrastructure.repository.PurchaseOrderRepository;
import ymyoo.utils.PrettySystemOut;

/**
 * Created by 유영모 on 2016-10-20.
 */
public class PurchaseOrderBusinessActivity extends AbstractBusinessActivity<ApprovalOrderPayment, Void> {
    private Order order;

    public PurchaseOrderBusinessActivity(Order order) {
        super(order.getOrderId(), OrderStatus.Status.PURCHASE_ORDER_REQUEST, OrderStatus.Status.PURCHASE_ORDER_CREATED);
        this.order = order;
    }

    @Override
    public Void performActivity(ApprovalOrderPayment approvalOrderPayment) {
        // 구매 주문 생성
        PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem(order.getOrderItem().getProductId(),
                order.getOrderItem().getOrderQty(), order.getOrderItem().getDeliveryType());
        PurchaseOrderPayment purchaseOrderPayment = new PurchaseOrderPayment(approvalOrderPayment.getTid(),
                order.getOrderPayment().getOrderAmount(), order.getOrderPayment().getCreditCardNo());
        Purchaser purchaser = new Purchaser(order.getOrderer().getName(), order.getOrderer().getContactNumber(), order.getOrderer().getEmail());

        PurchaseOrder purchaseOrder = PurchaseOrderFactory.create(order.getOrderId(),purchaser, purchaseOrderItem, purchaseOrderPayment);

        PurchaseOrderRepository repository = new PurchaseOrderRepository();
        repository.add(purchaseOrder);

        PrettySystemOut.println(order.getClass(), "주문 완료....");

        // 구매 주문 생성 이벤트 발행
        PurchaseOrderChannelAdapter channelAdapter = new PurchaseOrderChannelAdapter();
        channelAdapter.onPurchaseOrderCreated(purchaseOrder);
        return null;
    }
}

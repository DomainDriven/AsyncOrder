package ymyoo.order.domain.workflow;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import ymyoo.infra.messaging.remote.queue.ReplyMessage;
import ymyoo.inventory.exception.StockOutException;
import ymyoo.order.adapter.InventoryChannelAdapter;
import ymyoo.order.adapter.PaymentGatewayChannelAdapter;
import ymyoo.order.domain.Order;
import ymyoo.order.domain.event.OrderCompleted;
import ymyoo.order.domain.event.OrderFailed;
import ymyoo.order.domain.workflow.activity.impl.InventoryAsyncActivity;
import ymyoo.order.domain.workflow.activity.impl.PaymentGatewayAsyncActivity;
import ymyoo.order.domain.workflow.activity.impl.PurchaseOrderSyncActivity;
import ymyoo.order.domain.workflow.activity.SyncActivity;
import ymyoo.payment.ApprovalOrderPayment;
import ymyoo.order.domain.po.impl.DefaultPurchaseOrder;
import ymyoo.infra.messaging.local.EventPublisher;
import ymyoo.order.utility.PrettySystemOut;

/**
 * 배송 대행 상품 프로세서
 *
 * Created by 유영모 on 2016-10-24.
 */
public class AgencyDeliveryProductProcessor implements OrderProcessor {

    @Override
    public void runWorkflow(Order order) {
        /**
         * 비동기 Workflow
         *
         * 1. 재고 확인/예약 작업과 결제 인증/승인 작업 동시 실행
         * 2. 두개 작업 완료 시 구매 주문 생성 실행
         */
        // 재고 확인/예약 작업
        Observable inventorySequenceActivityObs = Observable.create((subscriber) -> {
                    InventoryAsyncActivity activity = new InventoryAsyncActivity(order);
                    activity.perform();
                    subscriber.onNext(activity);
                }
        ).flatMap(activity -> Observable.create((subscriber) -> {
            InventoryChannelAdapter channelAdapter = new InventoryChannelAdapter();
            ReplyMessage replyMessage = channelAdapter.listen(order.getOrderId());
            ((InventoryAsyncActivity)activity).callback(replyMessage);

            subscriber.onCompleted();
        })).subscribeOn(Schedulers.computation());

        // 결제 인증/승인 작업
        Observable<Object> paymentGatewaySequenceActivityObs = Observable.create(subscriber -> {
            PaymentGatewayAsyncActivity activity = new PaymentGatewayAsyncActivity(order);
            activity.perform();
            subscriber.onNext(activity);
        }).flatMap(activity -> Observable.create(subscriber -> {
            PaymentGatewayChannelAdapter channelAdapter = new PaymentGatewayChannelAdapter();
            ReplyMessage replyMessage = channelAdapter.listen(order.getOrderId());
            ((InventoryAsyncActivity)activity).callback(replyMessage);

            subscriber.onCompleted();
        })).subscribeOn(Schedulers.computation());

        Observable<Object> inventoryAndPaymentCompositeActivityObs =
                Observable.merge(inventorySequenceActivityObs, paymentGatewaySequenceActivityObs);

        Subscriber<Object> inventoryAndPaymentObsSubscriber = new Subscriber<Object>() {
            private ApprovalOrderPayment approvalOrderPayment;

            @Override
            public void onCompleted() {
                // 구매 주문 생성 작업
                SyncActivity<Void> activity =
                        new PurchaseOrderSyncActivity(order, new DefaultPurchaseOrder(), approvalOrderPayment);
                activity.perform();

                // 주문 성공 이벤트 게시
                EventPublisher.instance().publish(new OrderCompleted(order.getOrderId()));
            }

            @Override
            public void onError(Throwable throwable) {
                // 주문 실패 이벤트 게시
                if(throwable.getCause() instanceof StockOutException) {
                    PrettySystemOut.println(this.getClass(), "재고 없음 예외 발생");
                    EventPublisher.instance().publish(new OrderFailed(order.getOrderId(), "Stockout"));
                }
                EventPublisher.instance().publish(new OrderFailed(order.getOrderId(), ""));
            }

            @Override
            public void onNext(Object o) {
                this.approvalOrderPayment = (ApprovalOrderPayment)o;
            }
        };
        inventoryAndPaymentCompositeActivityObs.subscribe(inventoryAndPaymentObsSubscriber);

    }
}

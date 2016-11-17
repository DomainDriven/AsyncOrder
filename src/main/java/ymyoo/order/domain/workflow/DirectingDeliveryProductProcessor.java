package ymyoo.order.domain.workflow;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import ymyoo.inventory.exception.StockOutException;
import ymyoo.order.domain.Order;
import ymyoo.order.domain.event.OrderCompleted;
import ymyoo.order.domain.event.OrderFailed;
import ymyoo.order.domain.workflow.activity.InventorySequenceActivity;
import ymyoo.order.domain.workflow.activity.PaymentGatewaySequenceActivity;
import ymyoo.order.domain.workflow.activity.PurchaseOrderSequenceActivity;
import ymyoo.order.domain.workflow.activity.SequenceActivity;
import ymyoo.payment.ApprovalOrderPayment;
import ymyoo.order.domain.po.impl.DefaultPurchaseOrder;
import ymyoo.order.domain.po.impl.DirectDeliveryPurchaseOrder;
import ymyoo.infra.messaging.EventPublisher;
import ymyoo.order.utility.PrettySystemOut;

/**
 * 자사 배송 상품 프로세서
 *
 * Created by 유영모 on 2016-10-24.
 */
public class DirectingDeliveryProductProcessor implements OrderProcessor {

    @Override
    public void runWorkflow(Order order) {
        /**
         *  Workflow
         *
         * 1. 재고 확인/예약 작업과 결제 인증/승인 작업 동시 실행
         * 2. 두개 작업 완료 시 구매 주문 생성 실행
         */
        // 재고 확인/예약 작업
        Observable inventorySequenceActivityObs = Observable.create((subscriber) -> {
                    SequenceActivity<Void> activity = new InventorySequenceActivity(order);
                    activity.perform();
                    subscriber.onCompleted();
                }
        ).subscribeOn(Schedulers.computation());

        // 결제 인증/승인 작업
        Observable<Object> paymentGatewaySequenceActivityObs = Observable.create(subscriber -> {
            SequenceActivity<ApprovalOrderPayment> activity = new PaymentGatewaySequenceActivity(order);
            ApprovalOrderPayment approvalOrderPayment = activity.perform();
            subscriber.onNext(approvalOrderPayment);
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.computation());

        Observable<Object> inventoryAndPaymentCompositeActivityObs =
                Observable.merge(inventorySequenceActivityObs, paymentGatewaySequenceActivityObs);

        Subscriber<Object> inventoryAndPaymentObsSubscriber = new Subscriber<Object>() {
            private ApprovalOrderPayment approvalOrderPayment;

            @Override
            public void onCompleted() {
                // 구매 주문 생성 작업
                SequenceActivity<Void> activity = new PurchaseOrderSequenceActivity(
                        order, new DirectDeliveryPurchaseOrder(new DefaultPurchaseOrder()), approvalOrderPayment);
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

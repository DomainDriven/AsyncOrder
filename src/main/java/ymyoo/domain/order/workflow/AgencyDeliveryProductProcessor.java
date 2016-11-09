package ymyoo.domain.order.workflow;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import ymyoo.domain.inventory.exception.StockOutException;
import ymyoo.domain.inventory.impl.AgencyInventory;
import ymyoo.domain.inventory.impl.DirectingInventory;
import ymyoo.domain.order.Order;
import ymyoo.domain.order.event.OrderCompleted;
import ymyoo.domain.order.event.OrderFailed;
import ymyoo.domain.order.workflow.activity.InventorySequenceActivity;
import ymyoo.domain.order.workflow.activity.PaymentGatewaySequenceActivity;
import ymyoo.domain.order.workflow.activity.PurchaseOrderSequenceActivity;
import ymyoo.domain.order.workflow.activity.SequenceActivity;
import ymyoo.domain.payment.ApprovalOrderPayment;
import ymyoo.domain.purchaseorder.impl.DefaultPurchaseOrder;
import ymyoo.domain.purchaseorder.impl.DirectDeliveryPurchaseOrder;
import ymyoo.infra.messaging.EventPublisher;
import ymyoo.util.PrettySystemOut;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

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
                    SequenceActivity<Void> activity = new InventorySequenceActivity(order, new AgencyInventory());
                    activity.act();
                    subscriber.onCompleted();
                }
        ).subscribeOn(Schedulers.io());

        // 결제 인증/승인 작업
        Observable<Object> paymentGatewaySequenceActivityObs = Observable.create(subscriber -> {
            SequenceActivity<ApprovalOrderPayment> activity = new PaymentGatewaySequenceActivity(order);
            ApprovalOrderPayment approvalOrderPayment = activity.act();
            subscriber.onNext(approvalOrderPayment);
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.io());

        Observable<Object> inventoryAndPaymentCompositeActivityObs =
                Observable.merge(inventorySequenceActivityObs, paymentGatewaySequenceActivityObs);

        Subscriber<Object> inventoryAndPaymentObsSubscriber = new Subscriber<Object>() {
            private ApprovalOrderPayment approvalOrderPayment;

            @Override
            public void onCompleted() {
                // 구매 주문 생성 작업
                SequenceActivity<Void> activity =
                        new PurchaseOrderSequenceActivity(order, new DefaultPurchaseOrder(), approvalOrderPayment);
                activity.act();

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

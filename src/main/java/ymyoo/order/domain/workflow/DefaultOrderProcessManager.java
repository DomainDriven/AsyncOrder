package ymyoo.order.domain.workflow;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import ymyoo.infra.messaging.local.EventPublisher;
import ymyoo.inventory.exception.StockOutException;
import ymyoo.order.domain.event.OrderCompleted;
import ymyoo.order.domain.event.OrderFailed;
import ymyoo.order.domain.po.ApprovalOrderPayment;
import ymyoo.order.domain.so.SalesOrder;
import ymyoo.order.domain.workflow.activity.BusinessActivity;
import ymyoo.order.domain.workflow.activity.SyncBusinessActivity;
import ymyoo.order.domain.workflow.activity.impl.InventoryBusinessActivity;
import ymyoo.order.domain.workflow.activity.impl.PaymentGatewayBusinessActivity;
import ymyoo.order.domain.workflow.activity.impl.PurchaseOrderBusinessActivity;
import ymyoo.utility.PrettySystemOut;

/**
 * 주문 기본 프로세스 Manager
 *
 * Created by 유영모 on 2016-10-24.
 */
public class DefaultOrderProcessManager implements OrderProcessManager {

    @Override
    public void runWorkflow(SalesOrder salesOrder) {
        /**
         *  Workflow
         *
         * 1. 재고 확인/예약 작업과 결제 인증/승인 작업 동시 실행
         * 2. 두개 작업 완료 시 구매 주문 생성 실행
         */
        // 재고 확인/예약 작업
        Observable inventorySequenceActivityObs = Observable.create((subscriber) -> {
                    BusinessActivity<SalesOrder, Boolean> activity = new InventoryBusinessActivity();
                    boolean result = activity.perform(salesOrder);
                    if(result == true) {
                        System.out.println(this.getClass() + " - InventoryBusinessActivity :" + "subscriber.onCompleted() 호출");
                        subscriber.onCompleted();
                    } else {
                        throw new RuntimeException("재고 오류");
                    }
                }
        ).subscribeOn(Schedulers.computation());

        // 결제 인증/승인 작업
        Observable<Object> paymentGatewaySequenceActivityObs = Observable.create(subscriber -> {
            BusinessActivity<SalesOrder, ApprovalOrderPayment> activity = new PaymentGatewayBusinessActivity();
            ApprovalOrderPayment approvalOrderPayment = activity.perform(salesOrder);
            subscriber.onNext(approvalOrderPayment);
            subscriber.onCompleted();

        }).subscribeOn(Schedulers.computation());

        Observable<Object> inventoryAndPaymentCompositeActivityObs =
                Observable.merge(inventorySequenceActivityObs, paymentGatewaySequenceActivityObs);

        inventoryAndPaymentCompositeActivityObs.last().flatMap(approvalOrderPayment -> Observable.create(subscriber -> {
            // 구매 주문 생성 작업
            SyncBusinessActivity<ApprovalOrderPayment, Void> activity = new PurchaseOrderBusinessActivity(salesOrder);
            activity.perform((ApprovalOrderPayment)approvalOrderPayment);

            subscriber.onCompleted();
        })).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                // 주문 성공 이벤트 게시
                EventPublisher.instance().publish(new OrderCompleted(salesOrder.getOrderId()));
            }

            @Override
            public void onError(Throwable throwable) {
                // 주문 실패 이벤트 게시
                if (throwable.getCause() instanceof StockOutException) {
                    PrettySystemOut.println(this.getClass(), "재고 없음 예외 발생");
                    EventPublisher.instance().publish(new OrderFailed(salesOrder.getOrderId(), "Stockout"));
                }
                EventPublisher.instance().publish(new OrderFailed(salesOrder.getOrderId(), ""));
            }

            @Override
            public void onNext(Object o) {
            }
        });
    }

}

package ymyoo.app.order.domain.workflow;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import ymyoo.app.order.domain.Order;
import ymyoo.app.order.domain.OrderStatus;
import ymyoo.app.order.domain.po.ApprovalOrderPayment;
import ymyoo.app.order.domain.workflow.activity.BusinessActivity;
import ymyoo.app.order.domain.workflow.activity.impl.InventoryBusinessActivity;
import ymyoo.app.order.domain.workflow.activity.impl.PaymentGatewayBusinessActivity;
import ymyoo.app.order.domain.workflow.activity.impl.PurchaseOrderBusinessActivity;

/**
 * 주문 기본 프로세스 Manager
 *
 * Created by 유영모 on 2016-10-24.
 */
public class DefaultOrderProcessManager implements OrderProcessManager {

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
            BusinessActivity<Order, Boolean> activity = new InventoryBusinessActivity();
            activity.perform(order);

            subscriber.onCompleted();
        }).subscribeOn(Schedulers.computation());

        // 결제 인증/승인 작업
        Observable<Object> paymentGatewaySequenceActivityObs = Observable.create(subscriber -> {
            BusinessActivity<Order, ApprovalOrderPayment> activity = new PaymentGatewayBusinessActivity();
            ApprovalOrderPayment approvalOrderPayment = activity.perform(order);

            subscriber.onNext(approvalOrderPayment);
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.computation());

        Observable<Object> inventoryAndPaymentCompositeActivityObs =
                Observable.merge(inventorySequenceActivityObs, paymentGatewaySequenceActivityObs);

        inventoryAndPaymentCompositeActivityObs.last().flatMap(approvalOrderPayment -> Observable.create(subscriber -> {
            // 구매 주문 생성 작업
            BusinessActivity<ApprovalOrderPayment, Void> activity = new PurchaseOrderBusinessActivity(order);
            activity.perform((ApprovalOrderPayment)approvalOrderPayment);

            subscriber.onCompleted();
        })).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                // 주문 성공
            }

            @Override
            public void onError(Throwable throwable) {
                // 주문 실패
                throw new RuntimeException("주문 실패...");
            }

            @Override
            public void onNext(Object o) {
            }
        });
    }

}

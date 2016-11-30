package ymyoo.order.domain.workflow;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import ymyoo.inventory.exception.StockOutException;
import ymyoo.order.domain.ApprovalOrderPayment;
import ymyoo.order.domain.workflow.activity.BusinessActivity;
import ymyoo.order.domain.Order;
import ymyoo.order.domain.event.OrderCompleted;
import ymyoo.order.domain.event.OrderFailed;
import ymyoo.order.domain.workflow.activity.impl.InventoryBusinessActivity;
import ymyoo.order.domain.workflow.activity.impl.PaymentGatewayBusinessActivity;
import ymyoo.order.domain.workflow.activity.impl.PurchaseOrderBusinessActivity;
import ymyoo.order.domain.po.impl.DefaultPurchaseOrder;
import ymyoo.infra.messaging.local.EventPublisher;
import ymyoo.utility.PrettySystemOut;

/**
 * 배송 대행 상품 프로세스 관리자
 *
 * Created by 유영모 on 2016-10-24.
 */
public class AgencyDeliveryProductProcessManager implements OrderProcessManager {

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
                    BusinessActivity<Order, Void> activity = new InventoryBusinessActivity();
                    activity.perform(order);

                    subscriber.onCompleted();
                }
        ).subscribeOn(Schedulers.computation());

        // 결제 인증/승인 작업
        Observable<Object> paymentGatewaySequenceActivityObs = Observable.create(subscriber -> {
            BusinessActivity<Order, ApprovalOrderPayment> activity = new PaymentGatewayBusinessActivity();
            ApprovalOrderPayment approvalOrderPayment = activity.perform(order);

            subscriber.onNext(approvalOrderPayment);
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.computation());

        Observable<Object> inventoryAndPaymentCompositeActivityObs =
                Observable.merge(inventorySequenceActivityObs, paymentGatewaySequenceActivityObs);

        inventoryAndPaymentCompositeActivityObs.concatMap(approvalOrderPayment -> Observable.create(subscriber -> {
            // 구매 주문 생성 작업
            BusinessActivity<ApprovalOrderPayment, Void> activity =
                    new PurchaseOrderBusinessActivity(order, new DefaultPurchaseOrder());
            activity.perform((ApprovalOrderPayment) approvalOrderPayment);

            subscriber.onCompleted();

        })).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                // 주문 성공 이벤트 게시
                EventPublisher.instance().publish(new OrderCompleted(order.getOrderId()));
            }

            @Override
            public void onError(Throwable throwable) {
                // 주문 실패 이벤트 게시
                if (throwable.getCause() instanceof StockOutException) {
                    PrettySystemOut.println(this.getClass(), "재고 없음 예외 발생");
                    EventPublisher.instance().publish(new OrderFailed(order.getOrderId(), "Stockout"));
                }
                EventPublisher.instance().publish(new OrderFailed(order.getOrderId(), ""));
            }

            @Override
            public void onNext(Object o) {
            }
        });
    }
}

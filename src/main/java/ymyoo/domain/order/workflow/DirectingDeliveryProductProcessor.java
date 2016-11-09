package ymyoo.domain.order.workflow;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import ymyoo.domain.inventory.impl.DirectingInventory;
import ymyoo.domain.order.Order;
import ymyoo.domain.order.workflow.activity.InventorySequenceActivity;
import ymyoo.domain.order.workflow.activity.PaymentGatewaySequenceActivity;
import ymyoo.domain.order.workflow.activity.PurchaseOrderSequenceActivity;
import ymyoo.domain.order.workflow.activity.SequenceActivity;
import ymyoo.domain.payment.ApprovalOrderPayment;
import ymyoo.domain.purchaseorder.impl.DefaultPurchaseOrder;
import ymyoo.domain.purchaseorder.impl.DirectDeliveryPurchaseOrder;

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
        Observable<Void> inventorySequenceActivityObs = Observable.create((subscriber) -> {
                    SequenceActivity<Void> activity = new InventorySequenceActivity(order, new DirectingInventory());
                    activity.act();
                    subscriber.onCompleted();
                }
        );

        // 결제 인증/승인 작업
        Observable<ApprovalOrderPayment> paymentGatewaySequenceActivityObs = Observable.create(subscriber -> {
            SequenceActivity<ApprovalOrderPayment> activity = new PaymentGatewaySequenceActivity(order);
            ApprovalOrderPayment approvalOrderPayment = activity.act();
            subscriber.onNext(approvalOrderPayment);
            subscriber.onCompleted();
        });

        Observable<Object> inventoryAndPaymentCompositeActivityObs =
                Observable.merge(inventorySequenceActivityObs, paymentGatewaySequenceActivityObs);

        Subscriber<Object> inventoryAndPaymentObsSubscriber = new Subscriber<Object>() {
            private ApprovalOrderPayment approvalOrderPayment;

            @Override
            public void onCompleted() {
                // 구매 주문 생성 작업
                SequenceActivity<Void> activity = new PurchaseOrderSequenceActivity(
                        order, new DirectDeliveryPurchaseOrder(new DefaultPurchaseOrder()), approvalOrderPayment);
                activity.act();
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("inventoryAndPaymentObsSubscriber - onError");
                throw new RuntimeException(throwable);
            }

            @Override
            public void onNext(Object o) {
                System.out.println("inventoryAndPaymentObsSubscriber - onNext");
                this.approvalOrderPayment = (ApprovalOrderPayment)o;
            }
        };

        inventoryAndPaymentCompositeActivityObs.subscribe(inventoryAndPaymentObsSubscriber);
    }
}

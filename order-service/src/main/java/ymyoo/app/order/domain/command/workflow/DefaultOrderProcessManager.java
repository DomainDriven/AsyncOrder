package ymyoo.app.order.domain.command.workflow;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import ymyoo.app.order.domain.command.Order;
import ymyoo.app.order.domain.command.OrderStatus;
import ymyoo.app.order.domain.command.OrderStatusHistory;
import ymyoo.app.order.domain.command.po.ApprovalOrderPayment;
import ymyoo.app.order.domain.command.workflow.activity.BusinessActivity;
import ymyoo.app.order.domain.command.workflow.activity.impl.InventoryBusinessActivity;
import ymyoo.app.order.domain.command.workflow.activity.impl.PaymentGatewayBusinessActivity;
import ymyoo.app.order.domain.command.workflow.activity.impl.PurchaseOrderBusinessActivity;
import ymyoo.app.order.infrastructure.repository.OrderStatusRepository;

import java.util.Date;

/**
 * 주문 기본 프로세스 Manager
 *
 * Created by 유영모 on 2016-10-24.
 */
public class DefaultOrderProcessManager implements OrderProcessManager {
    private OrderStatusRepository orderStatusRepository = new OrderStatusRepository();

    @Override
    public void runWorkflow(Order order) {
        /**
         *  Workflow
         *
         * 1. 재고 확인/예약 작업과 결제 인증/승인 작업 동시 실행
         * 2. 두개 작업 완료 시 구매 주문 생성 실행
         */
        OrderStatus orderStatus = new OrderStatus(order.getOrderId(), OrderStatus.Status.ORDER_READY);
        OrderStatusHistory history = new OrderStatusHistory();
        history.setStatus(OrderStatus.Status.ORDER_READY);
        history.setCreatedDate(new Date());
        orderStatus.addHistory(history);
        orderStatusRepository.add(orderStatus);

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
//        order.setStatus(OrderStatus.PURCHASE_ORDER_CREATED);
                OrderStatus orderStatus = new OrderStatus(order.getOrderId(), OrderStatus.Status.PURCHASE_ORDER_CREATED);
                OrderStatusHistory history = new OrderStatusHistory();
                history.setStatus(OrderStatus.Status.PURCHASE_ORDER_CREATED);
                history.setCreatedDate(new Date());
                orderStatus.addHistory(history);

                orderStatusRepository.add(orderStatus);
            }

            @Override
            public void onError(Throwable throwable) {
                // 주문 실패
                OrderStatus orderStatus = new OrderStatus(order.getOrderId(), OrderStatus.Status.PURCHASE_ORDER_CREATED);
                OrderStatusHistory history = new OrderStatusHistory();
                history.setStatus(OrderStatus.Status.ORDER_FAILED);
                history.setCreatedDate(new Date());
                orderStatus.addHistory(history);

                orderStatusRepository.add(orderStatus);

                throw new RuntimeException("주문 실패...");
            }

            @Override
            public void onNext(Object o) {
            }
        });
    }

}

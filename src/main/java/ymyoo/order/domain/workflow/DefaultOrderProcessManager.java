package ymyoo.order.domain.workflow;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import ymyoo.infra.messaging.local.EventPublisher;
import ymyoo.infra.messaging.remote.channel.Callback;
import ymyoo.inventory.exception.StockOutException;
import ymyoo.order.domain.event.OrderCompleted;
import ymyoo.order.domain.event.OrderFailed;
import ymyoo.order.domain.po.ApprovalOrderPayment;
import ymyoo.order.domain.so.SalesOrder;
import ymyoo.order.domain.workflow.activity.AsyncBusinessActivity;
import ymyoo.order.domain.workflow.activity.SyncBusinessActivity;
import ymyoo.order.domain.workflow.activity.impl.InventoryBusinessActivity;
import ymyoo.order.domain.workflow.activity.impl.PaymentGatewayBusinessActivity;
import ymyoo.order.domain.workflow.activity.impl.PurchaseOrderBusinessActivity;
import ymyoo.utility.PrettySystemOut;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

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
                    AsyncBusinessActivity<SalesOrder, Boolean> activity = new InventoryBusinessActivity();
                    String activityId = activity.getId();
                    activity.perform(salesOrder, new Callback<Boolean>() {
                        @Override
                        public void call(Boolean result) {
                            if(result == false) {
                                throw new RuntimeException("재고 오류");
                            }

                            subscriber.onCompleted();
                        }

                        @Override
                        public Boolean translate(String data) {
                            Type type = new TypeToken<HashMap<String, String>>(){}.getType();
                            Map<String, String> content = new Gson().fromJson(data, type);
                            if(content.get("validation").equals("SUCCESS")) {
                                return true;
                            } else {
                                return false;
                            }
                        }

                        @Override
                        public String getId() {
                            return activityId;
                        }
                    });
                }
        ).subscribeOn(Schedulers.computation());

        // 결제 인증/승인 작업
        Observable<Object> paymentGatewaySequenceActivityObs = Observable.create(subscriber -> {
            AsyncBusinessActivity<SalesOrder, ApprovalOrderPayment> activity = new PaymentGatewayBusinessActivity();
            String activityId = activity.getId();
            activity.perform(salesOrder, new Callback<ApprovalOrderPayment>() {
                @Override
                public void call(ApprovalOrderPayment result) {
                    subscriber.onNext(result);
                    subscriber.onCompleted();
                }

                @Override
                public ApprovalOrderPayment translate(String data) {
                    return new Gson().fromJson(data, ApprovalOrderPayment.class);
                }

                @Override
                public String getId() {
                    return activityId;
                }
            });
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

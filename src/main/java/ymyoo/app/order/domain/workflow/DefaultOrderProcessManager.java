package ymyoo.app.order.domain.workflow;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import ymyoo.app.order.domain.Order;
import ymyoo.app.order.domain.po.ApprovalOrderPayment;
import ymyoo.app.order.domain.workflow.activity.AsyncBusinessActivity;
import ymyoo.app.order.domain.workflow.activity.BusinessActivity;
import ymyoo.app.order.domain.workflow.activity.impl.InventoryBusinessActivity;
import ymyoo.app.order.domain.workflow.activity.impl.PaymentGatewayBusinessActivity;
import ymyoo.app.order.domain.workflow.activity.impl.PurchaseOrderBusinessActivity;
import ymyoo.messaging.core.Callback;

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
    public void runWorkflow(Order order) {
        /**
         *  Workflow
         *
         * 1. 재고 확인/예약 작업과 결제 인증/승인 작업 동시 실행
         * 2. 두개 작업 완료 시 구매 주문 생성 실행
         */
        // 재고 확인/예약 작업
        Observable inventorySequenceActivityObs = Observable.create((subscriber) -> {
            AsyncBusinessActivity<Order, Boolean> activity = new InventoryBusinessActivity();
            String activityId = activity.getId();
            activity.perform(order, new Callback<Boolean>() {
                @Override
                public void call(Boolean result) {
                    if(result == false) {
                        throw new RuntimeException("재고 오류");
                    }

                    subscriber.onCompleted();
                }

                @Override
                public Boolean translate(String data) {
                    System.out.println("data : " + data);
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
        }).subscribeOn(Schedulers.computation());

        // 결제 인증/승인 작업
        Observable<Object> paymentGatewaySequenceActivityObs = Observable.create(subscriber -> {
            AsyncBusinessActivity<Order, ApprovalOrderPayment> activity = new PaymentGatewayBusinessActivity();
            String activityId = activity.getId();
            activity.perform(order, new Callback<ApprovalOrderPayment>() {
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

package ymyoo.order;

import ymyoo.order.event.OrderCompleteEvent;
import ymyoo.order.event.listener.OrderCompleteEventListener;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 유영모 on 2016-10-07.
 */
public class Order {
    public String placeOrder(OrderCompleteEventListener listener) {
        String orderId = OrderIdGenerator.generate();

        // TODO : 비동기로 수행할 작업 추가(결제 인증/승인, 재고 확인/확보)
        ExecutorService executor = Executors.newSingleThreadExecutor();
        CompletableFuture.runAsync(() -> {
            try {
                // 일단 Sleep으로 작업을 대신함.
                Thread.sleep(500);
                System.out.println("결제 인증.....");
                Thread.sleep(500);
                System.out.println("결제 승인.....");
                Thread.sleep(500);
                System.out.println("재고 확인.....");
                Thread.sleep(500);
                System.out.println("재고 확보.....");
                Thread.sleep(500);
                System.out.println("구매 주문 생성 ....");
            }
            catch(InterruptedException e) { }
        },executor).thenRun(() -> listener.setOrderCompleted(new OrderCompleteEvent(orderId)));

        return orderId;
    }
}

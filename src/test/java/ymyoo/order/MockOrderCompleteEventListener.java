package ymyoo.order;

import ymyoo.order.event.OrderCompleteEvent;
import ymyoo.order.event.OrderCompleteEventListener;

/**
 * 주문 완료 이벤트 리스너 테스트 구현체
 *
 * Created by 유영모 on 2016-10-07.
 */
public class MockOrderCompleteEventListener implements OrderCompleteEventListener {

    private OrderCompleteEvent event;

    public void setOrderCompleted(OrderCompleteEvent event) {
        this.event = event;
        synchronized (this) {
            notifyAll();
        }
    }

    public OrderCompleteEvent getOrderCompleteEvent() {
        return event;
    }
}

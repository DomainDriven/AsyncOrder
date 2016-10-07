package ymyoo.order.event.listener;

import ymyoo.order.event.OrderCompleteEvent;

/**
 * 주문 완료 이벤트 리스너
 * Created by 유영모 on 2016-10-07.
 */
public interface OrderCompleteEventListener {
    void setOrderCompleted(OrderCompleteEvent event);
}

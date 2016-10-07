package ymyoo.order;

import ymyoo.order.event.OrderCompleteEvent;
import ymyoo.order.event.listener.OrderCompleteEventListener;

/**
 * Created by 유영모 on 2016-10-07.
 */
public class Order {
    public String placeOrder(OrderCompleteEventListener listener) {
        String orderId = "12345678";

        listener.setOrderCompleted(new OrderCompleteEvent(orderId));

        return orderId;
    }
}

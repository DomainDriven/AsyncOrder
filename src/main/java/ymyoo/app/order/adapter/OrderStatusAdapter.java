package ymyoo.app.order.adapter;

import ymyoo.app.order.domain.status.OrderStatus;

/**
 * Created by 유영모 on 2017-01-10.
 */
public class OrderStatusAdapter {
    public OrderStatus getStatus(String orderId) {
        return OrderStatus.PURCHASE_ORDER_CREATED;
    }
}

package ymyoo.app.order.domain.command.workflow.activity.impl;

import ymyoo.app.order.adapter.messaging.InventoryChannelAdapter;
import ymyoo.app.order.domain.command.Order;
import ymyoo.app.order.domain.command.OrderStatus;

/**
 * 주문시 수행 해야할 재고 관련 작업 모음
 *
 * Created by 유영모 on 2016-10-10.
 */
public class InventoryBusinessActivity extends AbstractBusinessActivity<Order, Boolean> {

    public InventoryBusinessActivity(final String orderId) {
        super(orderId, OrderStatus.Status.INVENTORY_REQUEST, OrderStatus.Status.INVENTORY_CHECKED);
    }

    @Override
    public Boolean performActivity(Order order) {
        InventoryChannelAdapter channelAdapter = new InventoryChannelAdapter();
        return channelAdapter.checkAndReserveOrderItem(order.getOrderId(), order.getOrderItem());
    }
}

package ymyoo.app.order.domain.workflow.activity.impl;

import ymyoo.app.order.domain.Order;
import ymyoo.app.order.domain.workflow.activity.BusinessActivity;
import ymyoo.app.order.adapter.messaging.InventoryChannelAdapter;

/**
 * 주문시 수행 해야할 재고 관련 작업 모음
 *
 * Created by 유영모 on 2016-10-10.
 */
public class InventoryBusinessActivity implements BusinessActivity<Order, Boolean> {

    @Override
    public Boolean perform(Order order) {
        InventoryChannelAdapter channelAdapter = new InventoryChannelAdapter();
        return channelAdapter.checkAndReserveOrderItem(order.getOrderItem());
    }
}

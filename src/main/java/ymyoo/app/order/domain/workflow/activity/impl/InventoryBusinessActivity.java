package ymyoo.app.order.domain.workflow.activity.impl;

import ymyoo.app.order.adapter.messaging.InventoryChannelAdapter;
import ymyoo.app.order.domain.Order;
import ymyoo.app.order.domain.workflow.activity.AsyncBusinessActivity;
import ymyoo.messaging.core.Callback;

/**
 * 주문시 수행 해야할 재고 관련 작업 모음
 *
 * Created by 유영모 on 2016-10-10.
 */
public class InventoryBusinessActivity implements AsyncBusinessActivity<Order, Boolean> {
    private final String id = java.util.UUID.randomUUID().toString().toUpperCase();

//    @Override
//    public Boolean perform(Order order) {
//        InventoryChannelAdapter channelAdapter = new InventoryChannelAdapter();
//        return channelAdapter.checkAndReserveOrderItem(order.getOrderId(), order.getOrderItem());
//    }
//

    @Override
    public void perform(Order order, Callback<Boolean> callback) {
        InventoryChannelAdapter channelAdapter = new InventoryChannelAdapter();
        channelAdapter.checkAndReserveOrderItem(id, order.getOrderId(), order.getOrderItem(), callback);
    }

    @Override
    public String getId() {
        return id;
    }
}

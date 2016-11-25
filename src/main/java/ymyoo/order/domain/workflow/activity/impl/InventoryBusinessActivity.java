package ymyoo.order.domain.workflow.activity.impl;

import ymyoo.order.domain.Order;
import ymyoo.order.domain.workflow.activity.BusinessActivity;
import ymyoo.order.message.endpoint.InventoryChannelAdapter;

/**
 * 주문시 수행 해야할 재고 관련 작업 모음
 *
 * Created by 유영모 on 2016-10-10.
 */
public class InventoryBusinessActivity implements BusinessActivity<Order, Void> {
    private InventoryChannelAdapter channelAdapter = new InventoryChannelAdapter();

    @Override
    public Void perform(Order order) {
        boolean result = channelAdapter.checkAndReserveOrderItem(order.getOrderId(), order.getOrderItem());
        if(result == false) {
            throw new RuntimeException("재고 오류");
        }
        return null;
    }
}

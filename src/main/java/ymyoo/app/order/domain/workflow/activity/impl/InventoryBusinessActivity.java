package ymyoo.app.order.domain.workflow.activity.impl;

import ymyoo.app.order.domain.so.SalesOrder;
import ymyoo.app.order.domain.workflow.activity.BusinessActivity;
import ymyoo.app.order.adapter.messaging.InventoryMessagingAdapter;

/**
 * 주문시 수행 해야할 재고 관련 작업 모음
 *
 * Created by 유영모 on 2016-10-10.
 */
public class InventoryBusinessActivity implements BusinessActivity<SalesOrder, Boolean> {

    @Override
    public Boolean perform(SalesOrder salesOrder) {
        InventoryMessagingAdapter channelAdapter = new InventoryMessagingAdapter();
        return channelAdapter.checkAndReserveOrderItem(salesOrder.getOrderItem());
    }
}

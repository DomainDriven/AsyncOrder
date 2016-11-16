package ymyoo.order.domain.workflow.activity;

import ymyoo.order.adapter.InventoryAdapter;
import ymyoo.order.domain.Order;

/**
 * 주문시 수행 해야할 재고 관련 작업 모음
 *
 * Created by 유영모 on 2016-10-10.
 */
public class InventorySequenceActivity implements  SequenceActivity<Void> {
    private Order order;

    public InventorySequenceActivity(Order order) {
        this.order = order;

    }

    @Override
    public Void perform() {
        InventoryAdapter adapter = new InventoryAdapter();
        adapter.checkAndReserveOrderItem(order.getOrderId(), order.getOrderItem());

        return null;
    }
}

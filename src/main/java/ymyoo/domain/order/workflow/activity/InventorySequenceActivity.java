package ymyoo.domain.order.workflow.activity;

import ymyoo.domain.inventory.Inventory;
import ymyoo.domain.order.Order;

/**
 * 주문시 수행 해야할 재고 관련 작업 모음
 *
 * Created by 유영모 on 2016-10-10.
 */
public class InventorySequenceActivity implements  SequenceActivity<Void> {

    private Order order;
    private Inventory inventory;

    public InventorySequenceActivity(Order order, Inventory inventory) {
        this.order = order;
        this.inventory = inventory;
    }

    @Override
    public Void act() {
        inventory.check(this.order.getOrderItem());
        inventory.reserve(this.order.getOrderItem());

        return null;
    }
}

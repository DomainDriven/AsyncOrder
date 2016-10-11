package ymyoo.order.inventory;

import ymyoo.order.OrderItem;

import java.util.function.Supplier;

/**
 * Created by 유영모 on 2016-10-10.
 */
public class InventoryTask implements Supplier<Void> {

    private OrderItem orderItem;

    public InventoryTask(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    @Override
    public Void get() {
        Inventory inventory = new Inventory();
        inventory.check(this.orderItem);
        inventory.reserve(this.orderItem);
        return null;
    }
}

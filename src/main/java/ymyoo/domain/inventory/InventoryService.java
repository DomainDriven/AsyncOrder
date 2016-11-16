package ymyoo.domain.inventory;

import ymyoo.domain.inventory.impl.AgencyInventory;
import ymyoo.domain.inventory.impl.DirectingInventory;
import ymyoo.domain.order.OrderItem;
import ymyoo.domain.order.OrderItemDeliveryType;

/**
 * Created by 유영모 on 2016-11-16.
 */
public class InventoryService {
    public void checkAndReserve(OrderItem item) {
        Inventory inventory;
        if(item.getDeliveryType() == OrderItemDeliveryType.DIRECTING) {
            inventory = new DirectingInventory();
        } else {
            inventory = new AgencyInventory();
        }
        inventory.check(item);
        inventory.reserve(item);
    }
}

package ymyoo.inventory;

import ymyoo.inventory.impl.AgencyInventory;
import ymyoo.inventory.impl.DirectingInventory;
import ymyoo.order.domain.OrderItemDeliveryType;

/**
 * Created by 유영모 on 2016-11-16.
 */
public class InventoryService {
    public void checkAndReserve(TakingOrderItem item) {
        Inventory inventory;
        if(item.getDeliveryType() == TakingOrderItem.DeliveryType.DIRECTING) {
            inventory = new DirectingInventory();
        } else {
            inventory = new AgencyInventory();
        }
        inventory.check(item);
        inventory.reserve(item);
    }
}

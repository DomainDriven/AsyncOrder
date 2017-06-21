package ymyoo.app.inventory.domain;

import ymyoo.app.inventory.domain.impl.AgencyInventory;
import ymyoo.app.inventory.domain.impl.DirectingInventory;

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

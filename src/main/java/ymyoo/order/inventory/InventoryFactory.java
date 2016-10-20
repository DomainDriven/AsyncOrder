package ymyoo.order.inventory;

import ymyoo.order.OrderItemDeliveryType;
import ymyoo.order.inventory.exception.UnSupportedDeliveryTypeException;
import ymyoo.order.inventory.impl.AgencyInventory;
import ymyoo.order.inventory.impl.DirectingInventory;

/**
 * 재고 팩토리
 *
 * Created by 유영모 on 2016-10-20.
 */
public class InventoryFactory {
    public static Inventory create(OrderItemDeliveryType deliveryType) {
        if(deliveryType == OrderItemDeliveryType.DIRECTING) {
            return new DirectingInventory();
        } else if (deliveryType == OrderItemDeliveryType.AGENCY) {
            return new AgencyInventory();
        } else {
            throw new UnSupportedDeliveryTypeException();
        }
    }
}

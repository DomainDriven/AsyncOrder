package ymyoo.order.inventory;

import ymyoo.order.OrderItemDeliveryType;
import ymyoo.order.inventory.exception.UnSupportedDeliveryTypeException;

/**
 * 재고 팩토리
 *
 * Created by 유영모 on 2016-10-20.
 */
public class InventoryFactory {
    public static Inventory create(OrderItemDeliveryType deliveryType) {
        if(deliveryType == OrderItemDeliveryType.DIRECTING) {
            return new DirectDeliveryInventory();
        } else if (deliveryType == OrderItemDeliveryType.AGENCY) {
            return new AgencyDeliveryInventory();
        } else {
            throw new UnSupportedDeliveryTypeException();
        }
    }
}

package ymyoo.order.inventory;

import ymyoo.order.Order;
import ymyoo.order.OrderItemDeliveryType;
import ymyoo.order.inventory.exception.UnSupportedDeliveryTypeException;

import java.util.function.Supplier;

/**
 * 주문시 수행 해야할 재고 관련 작업 모음
 *
 * {@link java.util.concurrent.CompletableFuture}를 사용하여 비동기 처리 하기 위해
 * {@link java.util.function.Supplier}를 구현함.
 *
 * Created by 유영모 on 2016-10-10.
 */
public class InventoryTransaction implements Supplier<Void> {

    private Order order;

    public InventoryTransaction(Order order) {
        this.order = order;
    }

    @Override
    public Void get() {
        Inventory inventory;

        if(order.getOrderItem().getDeliveryType() == OrderItemDeliveryType.DIRECTING) {
            inventory = new DirectDeliveryInventory();
        } else if (order.getOrderItem().getDeliveryType() == OrderItemDeliveryType.AGENCY) {
            inventory = new AgencyDeliveryInventory();
        } else {
            throw new UnSupportedDeliveryTypeException();
        }

        inventory.check(this.order.getOrderItem());
        inventory.reserve(this.order.getOrderItem());

        return null;
    }
}

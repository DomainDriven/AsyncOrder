package ymyoo.order.inventory;

import ymyoo.order.OrderItem;

import java.util.function.Supplier;

/**
 * 주문시 수행 해야할 재고 관련 작업 모음
 *
 * {@link java.util.concurrent.CompletableFuture}를 사용하여 비동기 처리 하기 위해
 * {@link java.util.function.Supplier}를 구현함.
 *
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
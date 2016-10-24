package ymyoo.domain.order.workflow.activity;

import ymyoo.domain.inventory.Inventory;
import ymyoo.domain.order.Order;

import java.util.function.Supplier;

/**
 * 주문시 수행 해야할 재고 관련 작업 모음
 *
 * {@link java.util.concurrent.CompletableFuture}를 사용하여 비동기 처리 하기 위해
 * {@link java.util.function.Supplier}를 구현함.
 *
 * Created by 유영모 on 2016-10-10.
 */
public class InventorySequenceActivity implements Supplier<Void> {

    private Order order;
    private Inventory inventory;

    public InventorySequenceActivity(Order order, Inventory inventory) {
        this.order = order;
        this.inventory = inventory;
    }

    @Override
    public Void get() {
        inventory.check(this.order.getOrderItem());
        inventory.reserve(this.order.getOrderItem());
        return null;
    }
}

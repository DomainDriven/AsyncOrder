package ymyoo.order.inventory;

import ymyoo.order.OrderItem;
import ymyoo.util.PrettySystemOut;

/**
 * 자사 배송 상품 재고 구현체
 *
 * Created by 유영모 on 2016-10-19.
 */
public class DirectingInventory implements Inventory {

    @Override
    public void check(OrderItem product) {
        try { Thread.sleep(250); } catch (InterruptedException e) {}
        PrettySystemOut.println(this.getClass(), "재고 확인-" + "상품 번호" + product.getProductId());
    }

    @Override
    public void reserve(OrderItem product) {
        try { Thread.sleep(250); } catch (InterruptedException e) {}
        PrettySystemOut.println(this.getClass(), "재고 확보-" + "상품 번호" + product.getProductId());
    }

    @Override
    public void reduction(OrderItem product) {
        try { Thread.sleep(250); } catch (InterruptedException e) {}
        PrettySystemOut.println(this.getClass(), "재고 차감-" + "상품 번호" + product.getProductId() + ", 수량 : " + product.getOrderQty());
    }
}

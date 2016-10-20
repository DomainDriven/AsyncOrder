package ymyoo.order.inventory.impl;

import ymyoo.order.OrderItem;
import ymyoo.order.inventory.Inventory;
import ymyoo.order.inventory.exception.StockOutException;
import ymyoo.util.PrettySystemOut;

/**
 * 배송 대행 상품 재고 구현체
 *
 * Created by 유영모 on 2016-10-19.
 */
public class AgencyInventory implements Inventory {

    @Override
    public void check(OrderItem product) {
        // 재고 없음 예외
        if(product.getProductId().equals("P0002")) {
            throw new StockOutException();
        }

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

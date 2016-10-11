package ymyoo.order.inventory;

import ymyoo.order.OrderItem;
import ymyoo.order.inventory.exception.StockOutException;
import ymyoo.util.PrettySystemOut;

/**
 * 재고
 *
 * Created by 유영모 on 2016-10-07.
 */
public class Inventory {
    /**
     * 상품 재고 확인
     * @param product
     * @return
     */
    public void check(OrderItem product) {
        // 재고 없음 예외
        if(product.getProductId().equals("P0002")) {
            throw new StockOutException();
        }

        try { Thread.sleep(250); } catch (InterruptedException e) {}
        PrettySystemOut.println(this.getClass(), "재고 확인-" + "상품 번호" + product.getProductId());
    }

    /**
     * 상품 재고 확보
     * @param product
     */
    public void reserve(OrderItem product) {
        try { Thread.sleep(250); } catch (InterruptedException e) {}
        PrettySystemOut.println(this.getClass(), "재고 확보-" + "상품 번호" + product.getProductId());
    }
}

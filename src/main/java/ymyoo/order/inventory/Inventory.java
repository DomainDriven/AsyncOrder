package ymyoo.order.inventory;

import ymyoo.order.OrderItem;

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
        try { Thread.sleep(250); } catch (InterruptedException e) {}
        System.out.println("[Current Thread ID - " + Thread.currentThread().getId() + "][Inventory Task]" +
                "상품 번호" + product.getProductId() + "-> 상품 재고 확인");
    }

    /**
     * 상품 재고 확보
     * @param product
     */
    public void reserve(OrderItem product) {
        try { Thread.sleep(250); } catch (InterruptedException e) {}
        System.out.println("[Current Thread ID - " + Thread.currentThread().getId() + "][Inventory Task]" +
                "상품 번호" + product.getProductId() + "-> 재고 확보 " + product.getOrderCount() + "개");
    }
}

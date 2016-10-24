package ymyoo.domain.inventory;

import ymyoo.domain.order.OrderItem;

/**
 * 상품 재고 인터페이스
 *
 * Created by 유영모 on 2016-10-19.
 */
public interface Inventory {
    /**
     * 상품 재고 확인
     * @param product
     * @return
     */
    void check(OrderItem product);

    /**
     * 상품 재고 확보
     * @param product
     */
    void reserve(OrderItem product);

    /**
     * 재고 차감
     * @param product
     */
    void reduction(OrderItem product);
}

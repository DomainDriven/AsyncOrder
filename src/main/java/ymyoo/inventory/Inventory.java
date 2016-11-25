package ymyoo.inventory;

/**
 * 상품 재고 인터페이스
 *
 * Created by 유영모 on 2016-10-19.
 */
public interface Inventory {
    /**
     * 상품 재고 확인
     * @param item
     * @return
     */
    void check(TakingOrderItem item);

    /**
     * 상품 재고 확보
     * @param item
     */
    void reserve(TakingOrderItem item);

    /**
     * 재고 차감
     * @param item
     */
    void reduction(TakingOrderItem item);
}

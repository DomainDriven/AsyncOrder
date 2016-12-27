package ymyoo.app.inventory.impl;

import ymyoo.app.inventory.Inventory;
import ymyoo.app.inventory.TakingOrderItem;
import ymyoo.app.inventory.exception.StockOutException;
import ymyoo.utility.PrettySystemOut;

/**
 * 배송 대행 상품 재고 구현체
 *
 * Created by 유영모 on 2016-10-19.
 */
public class AgencyInventory implements Inventory {
    @Override
    public void check(TakingOrderItem item) {
        // 재고 없음 예외
        if(item.getProductId().equals("P0002")) {
            throw new StockOutException();
        }

        try { Thread.sleep(250); } catch (InterruptedException e) {}
        PrettySystemOut.println(this.getClass(), "재고 확인-" + "상품 번호" + item.getProductId());
    }

    @Override
    public void reserve(TakingOrderItem item) {
        try { Thread.sleep(250); } catch (InterruptedException e) {}
        PrettySystemOut.println(this.getClass(), "재고 확보-" + "상품 번호" + item.getProductId());
    }

    @Override
    public void reduction(TakingOrderItem item) {
        try { Thread.sleep(250); } catch (InterruptedException e) {}
        PrettySystemOut.println(this.getClass(), "재고 차감-" + "상품 번호" + item.getProductId() + ", 수량 : " + item.getOrderQty());
    }
}

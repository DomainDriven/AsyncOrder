package ymyoo.app.order.domain.event;

/**
 * 주문 완료 이벤트
 *
 * Created by 유영모 on 2016-10-11.
 */
public class OrderCompleted implements OrderEvent {
    private String orderId;

    public OrderCompleted(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String getOrderId() {
        return this.orderId;
    }
}

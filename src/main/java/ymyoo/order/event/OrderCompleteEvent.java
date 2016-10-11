package ymyoo.order.event;

/**
 * 주문 완료 이벤트
 *
 * Created by 유영모 on 2016-10-07.
 */
public class OrderCompleteEvent {
    private String orderId;

    public OrderCompleteEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return this.orderId;
    }
}

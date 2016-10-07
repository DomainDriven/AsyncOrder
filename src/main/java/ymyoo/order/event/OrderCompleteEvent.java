package ymyoo.order.event;

/**
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

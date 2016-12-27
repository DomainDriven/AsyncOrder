package ymyoo.app.order.domain.event;

/**
 * 주문 실패 이벤트
 * Created by 유영모 on 2016-10-11.
 */
public class OrderFailed implements OrderEvent {
    private String orderId;
    private String errorMsg;

    public OrderFailed(String orderId, String errorMsg) {
        this.orderId = orderId;
        this.errorMsg = errorMsg;
    }

    @Override
    public String getOrderId() {
        return this.orderId;
    }

    public String getErrorMsg(){
        return this.errorMsg;
    }
}

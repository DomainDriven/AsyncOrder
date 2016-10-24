package ymyoo.domain.order.event;

/**
 * 주문 영역에서 일어나는 이벤트의 최상위 인터페이스
 *
 * Created by 유영모 on 2016-10-11.
 */
public interface OrderEvent {
    String getOrderId();
}

package ymyoo.order.event.messaging;

/**
 * 이벤트 구독자
 *
 * Created by 유영모 on 2016-10-11.
 */
public interface EventSubscriber<T> {
    void handleEvent(final T event);
    Class<T> subscribedToEventType();
}
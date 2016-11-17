package ymyoo.infra.messaging.local;

import ymyoo.order.domain.event.OrderEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * 이벤트 발행자
 *
 * Created by 유영모 on 2016-10-11.
 */
public class EventPublisher {
    private static EventPublisher instance = null;
    private static final List<EventSubscriber> subscribers = new ArrayList<EventSubscriber>();

    private EventPublisher() {
        super();
    }

    public static EventPublisher instance() {
        if(instance == null) {
            instance = new EventPublisher();
        }
        return instance;
    }

    public <T> void publish(final T aDomainEvent) {
        if (subscribers != null) {
            Class<?> eventType = aDomainEvent.getClass();
            for (EventSubscriber<T> subscriber : subscribers) {
                Class<?> subscribedTo = subscriber.subscribedToEventType();
                if (subscribedTo == eventType || subscribedTo == OrderEvent.class) {
                    subscriber.handleEvent(aDomainEvent);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> void subscribe(EventSubscriber<T> aSubscriber) {
        subscribers.add(aSubscriber);
    }

}

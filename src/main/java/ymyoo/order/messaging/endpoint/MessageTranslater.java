package ymyoo.order.messaging.endpoint;

/**
 * Created by 유영모 on 2016-12-23.
 */
public interface MessageTranslater<T> {
    T translate(String data);
}

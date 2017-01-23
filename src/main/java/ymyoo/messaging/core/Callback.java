package ymyoo.messaging.core;

/**
 * Created by 유영모 on 2017-01-23.
 */
public interface Callback<T> {
    void call(T result);
    T translate(String data);
    String getId();
}

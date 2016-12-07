package ymyoo.infra.messaging.remote.channel;

/**
 * Created by 유영모 on 2016-12-07.
 */
public interface Callback<T> {
    void call(T result);
    T translate(String data);
    String getId();
}

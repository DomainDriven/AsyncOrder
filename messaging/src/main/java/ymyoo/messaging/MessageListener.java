package ymyoo.messaging;

/**
 * Created by 유영모 on 2016-12-23.
 */
public interface MessageListener {
    void onMessage(Message message);
    String getCorrelationId();
}

package ymyoo.infra.messaging.remote.queue;

/**
 * Created by 유영모 on 2016-11-15.
 */
public class RequestMessage  extends Message {
    public enum MessageType {
        CHECK_INVENTOY
    }

    private MessageType type;

    public RequestMessage(String id, Object body, MessageType type) {
        super(id, body);
        this.type = type;
    }

    public MessageType getType() {
        return type;
    }
}

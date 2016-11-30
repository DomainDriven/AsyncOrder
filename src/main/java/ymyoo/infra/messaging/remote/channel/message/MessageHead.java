package ymyoo.infra.messaging.remote.channel.message;

/**
 * Created by 유영모 on 2016-11-25.
 */
public class MessageHead {
    public enum MessageType {
        CHECK_INVENTOY, AUTH_APV_PAYMENT
    }

    private MessageType type;
    private String id;

    public MessageHead(String id, MessageType type) {
        this.type = type;
        this.id = id;
    }

    public MessageType getType() {
        return type;
    }

    public String getId() {
        return id;
    }
}

package ymyoo.infra.messaging.remote.queue;

/**
 * Created by 유영모 on 2016-11-17.
 */
public class ReplyMessage extends Message {
    public enum ReplyMessageStatus {
        SUCCESS, FAILURE
    }

    private ReplyMessageStatus status;

    public ReplyMessage(String id, Object body, ReplyMessageStatus status) {
        super(id, body);
        this.status = status;
    }

    public ReplyMessageStatus getStatus() {
        return status;
    }
}

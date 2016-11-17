package ymyoo.infra.messaging.remote.queue;

/**
 * Created by 유영모 on 2016-11-17.
 */
public class Message {
    private String id;
    private Object body;

    public Message(String id, Object body) {
        this.id = id;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public Object getBody() {
        return body;
    }
}

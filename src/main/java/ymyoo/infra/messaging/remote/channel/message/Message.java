package ymyoo.infra.messaging.remote.channel.message;

import java.util.Map;

/**
 * Created by 유영모 on 2016-11-17.
 */
public class Message {
    private MessageHead head;
    private Map<String, String> body;

    public Message(MessageHead head, Map<String, String> body) {
        this.head = head;
        this.body = body;
    }

    public MessageHead getHead() {
        return head;
    }

    public Map<String, String> getBody() {
        return body;
    }
}

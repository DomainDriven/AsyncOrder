package ymyoo.infra.messaging.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 유영모 on 2017-02-01.
 */
public class Message {
    private final String messageId;
    private final Map<String, String> headers;
    private final Object body;

    public Message(String messageId, Map<String, String> headers, Object body) {
        this.messageId = messageId;
        this.headers = headers;
        this.body = body;
    }

    public Message(String messageId, Object body) {
        this.messageId = messageId;
        this.body = body;
        this.headers = new HashMap<>();
    }

    public String getMessageId() {
        return messageId;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Object getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "MessageWrapper{" +
                "messageId='" + messageId + '\'' +
                ", headers=" + headers +
                ", body=" + body +
                '}';
    }
}


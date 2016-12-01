package ymyoo.infra.messaging.remote.channel.message;

/**
 * Created by 유영모 on 2016-11-17.
 */
public class Message {
    private MessageHead head;
    private String body;

    public Message(MessageHead head, String body) {
        this.head = head;
        this.body = body;
    }

    public MessageHead getHead() {
        return head;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Message{" +
                "head=" + head +
                ", body='" + body + '\'' +
                '}';
    }
}

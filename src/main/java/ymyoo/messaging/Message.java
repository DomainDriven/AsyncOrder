package ymyoo.messaging;

/**
 * Created by 유영모 on 2016-12-29.
 */
public class Message {
    private String id;
    private String body;

    public Message(String id, String body) {
        this.id = id;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}

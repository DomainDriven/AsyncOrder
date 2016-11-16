package ymyoo.order.infra.messaging.queue;

/**
 * Created by 유영모 on 2016-11-15.
 */
public class Message {
    private String id;
    private MessageType type;
    private Object objectProperty;

    public Message() {
    }

    public Message(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Object getObjectProperty() {
        return objectProperty;
    }

    public void setObjectProperty(Object objectProperty) {
        this.objectProperty = objectProperty;
    }
}

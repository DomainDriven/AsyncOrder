package spike;

/**
 * Created by 유영모 on 2016-12-05.
 */
public class KafkaMessageHead {
    private String id;
    private String replyChannel;

    public KafkaMessageHead(String id, String replyChannel) {
        this.id = id;
        this.replyChannel = replyChannel;
    }

    public String getId() {
        return id;
    }

    public String getReplyChannel() {
        return replyChannel;
    }
}

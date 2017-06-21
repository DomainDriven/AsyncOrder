package ymyoo.infra.messaging;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 유영모 on 2017-01-02.
 */
public class TestReplier extends AbstractReplier {
    private List<Message> messageList = new ArrayList<>();
    public TestReplier(String channel) {
        super(channel);
    }

    @Override
    public void onMessage(Message message) {
        messageList.add(message);
    }

    public List<Message> getMessageList() {
        return messageList;
    }
}

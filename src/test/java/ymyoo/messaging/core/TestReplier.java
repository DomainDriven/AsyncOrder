package ymyoo.messaging.core;

import ymyoo.messaging.core.AbstractReplier;
import ymyoo.messaging.core.Message;

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
    public void onMessage(String replyChannel, Message message) {
        messageList.add(message);
    }

    public List<Message> getMessageList() {
        return messageList;
    }
}

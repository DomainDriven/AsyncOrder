package ymyoo.messaging.core;

import java.util.List;

/**
 * Created by 유영모 on 2017-01-02.
 */
public abstract class AbstractReplier implements Runnable {
    private final String channel;

    public AbstractReplier(String channel) {
        this.channel = channel;
    }

    public abstract void onMessage(String replyChannel, Message message);

    @Override
    public void run() {
        MessageConsumer messageConsumer = new MessageConsumer(channel);
        try {
            while (!Thread.currentThread().isInterrupted()) {
                List<Message> messages = messageConsumer.poll();
                for(Message message : messages) {
                    String replyChannel = extractReplyChannel(message.getId());
                    message.setId(extractMessageId(message.getId()));

                    onMessage(replyChannel, message);
                }
            }
        } finally {
            messageConsumer.close();
        }
    }

    private String extractReplyChannel(String str) {
        return str.substring(str.indexOf("::") + 2, str.length());
    }

    private String extractMessageId(String str) {
        if(str.contains("::")) {
            return str.substring(0, str.indexOf("::"));
        } else {
            return str;
        }
    }
}

package ymyoo.infra.messaging.remote.channel;

import ymyoo.infra.messaging.remote.channel.message.Message;

import java.util.concurrent.BlockingQueue;

/**
 * Created by 유영모 on 2016-11-16.
 */
public class MessageConsumer {
    private BlockingQueue<Message> destination;

    public MessageConsumer(BlockingQueue<Message> destination) {
        this.destination = destination;
    }

    public Message receive(String messageId) {
        while (!Thread.currentThread().isInterrupted()) {
            Message msg = null;
            try {
                msg = destination.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (msg.getHead().getId().equals(messageId)) {
                return msg;
            } else {
                destination.add(msg);
            }
        }

        return null;
    }
}

package ymyoo.infra.messaging.remote.channel;

import ymyoo.infra.messaging.remote.channel.message.Message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by 유영모 on 2016-11-16.
 */
public class MessageConsumer {
    private BlockingQueue<Message> destination;

    public MessageConsumer(BlockingQueue<Message> destination) {
        this.destination = destination;
    }

    public Message receive(String messageId) {
        Message receivedMessage = null;

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message msg =  destination.poll(10, TimeUnit.MILLISECONDS);
                if(msg == null) {
                    continue;
                } else {
                    if (msg.getHead().getId().equals(messageId)) {
                        receivedMessage = msg;
                        break;
                    } else {
                        destination.add(msg);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        return receivedMessage;
    }
}

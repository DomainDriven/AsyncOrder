package ymyoo.infra.messaging.remote.queue;

import java.util.concurrent.BlockingQueue;

/**
 * Created by 유영모 on 2016-11-16.
 */
public class MessageConsumer {
    private BlockingQueue<Message> destination;

    public MessageConsumer(BlockingQueue<Message> destination) {
        this.destination = destination;
    }

    public ReplyMessage receive(String messageId) {
        while(true) {
            try {
                Thread.sleep(1000);
                while(!Thread.currentThread().isInterrupted()) {
                    ReplyMessage msg = (ReplyMessage)destination.take();
                    if(msg.getId().equals(messageId)) {
                        return msg;
                    } else {
                        destination.add(msg);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

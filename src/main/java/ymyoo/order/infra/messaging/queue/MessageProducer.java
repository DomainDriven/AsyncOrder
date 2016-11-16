package ymyoo.order.infra.messaging.queue;

import java.util.concurrent.BlockingQueue;

/**
 * Created by 유영모 on 2016-11-15.
 */
public class MessageProducer {
    private BlockingQueue<Message> destination;

    public MessageProducer(BlockingQueue<Message> destination) {
        this.destination = destination;
    }

    public void send(Message msg) {
        try {
            destination.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
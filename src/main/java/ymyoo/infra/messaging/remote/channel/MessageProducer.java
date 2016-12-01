package ymyoo.infra.messaging.remote.channel;

import ymyoo.infra.messaging.remote.channel.message.Message;

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
        destination.add(msg);
    }
}

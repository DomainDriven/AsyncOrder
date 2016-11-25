package ymyoo.infra.messaging.remote.queue;

import ymyoo.infra.messaging.remote.queue.blockingqueue.ReplyBlockingQueue;
import ymyoo.infra.messaging.remote.queue.blockingqueue.RequestBlockingQueue;
import ymyoo.infra.messaging.remote.queue.message.Message;

/**
 * Created by 유영모 on 2016-11-16.
 */
public class Requester {

    public void send(Message message) {
        MessageProducer messageProducer = new MessageProducer(RequestBlockingQueue.getBlockingQueue());
        messageProducer.send(message);
    }

    public Message receive(String msgId) {
        MessageConsumer consumer = new MessageConsumer(ReplyBlockingQueue.getBlockingQueue());
        return consumer.receive(msgId);
    }
}

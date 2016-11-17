package ymyoo.infra.messaging.remote.queue;

import ymyoo.infra.messaging.remote.queue.blockingqueue.ReplyBlockingQueue;
import ymyoo.infra.messaging.remote.queue.blockingqueue.RequestBlockingQueue;

/**
 * Created by 유영모 on 2016-11-16.
 */
public class Requester {

    public void send(RequestMessage sendRequestMessage) {
        MessageProducer messageProducer = new MessageProducer(RequestBlockingQueue.getBlockingQueue());
        messageProducer.send(sendRequestMessage);
    }

    public ReplyMessage receive(String msgId) {
        MessageConsumer consumer = new MessageConsumer(ReplyBlockingQueue.getBlockingQueue());
        return consumer.receive(msgId);
    }
}

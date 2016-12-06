package ymyoo.infra.messaging.remote.channel;

import ymyoo.infra.messaging.remote.channel.blockingqueue.ReplyBlockingQueue;
import ymyoo.infra.messaging.remote.channel.blockingqueue.RequestBlockingQueue;
import ymyoo.infra.messaging.remote.channel.message.Message;

/**
 * Created by 유영모 on 2016-11-16.
 */
public class Requester {

    public void send(Message message) {
        LegacyMessageProducer messageProducer = new LegacyMessageProducer(RequestBlockingQueue.getBlockingQueue());
        messageProducer.send(message);
    }

    public Message receive(String msgId) {
        LegacyMessageConsumer consumer = new LegacyMessageConsumer(ReplyBlockingQueue.getBlockingQueue());
        return consumer.receive(msgId);
    }
}

package ymyoo.infra.messaging.queue;

import ymyoo.infra.messaging.queue.impl.RequestBlockingQueue;

/**
 * Created by 유영모 on 2016-11-15.
 */
public class MessageProducer {

    public void send(Message msg) {
        RequestBlockingQueue.getInstance().put(msg);
    }
}

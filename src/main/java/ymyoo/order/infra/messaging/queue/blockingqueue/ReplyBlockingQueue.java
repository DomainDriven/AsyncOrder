package ymyoo.order.infra.messaging.queue.blockingqueue;

import ymyoo.order.infra.messaging.queue.Message;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by 유영모 on 2016-11-15.
 */
public class ReplyBlockingQueue {

    private static ReplyBlockingQueue self = null;

    private static BlockingQueue<Message> queue = new ArrayBlockingQueue<>(10);

    private ReplyBlockingQueue() {
    }

    public static BlockingQueue<Message> getBlockingQueue() {
        if(self == null) {
            self =  new ReplyBlockingQueue();
            return self.queue;
        } else {
            return self.queue;
        }
    }

}

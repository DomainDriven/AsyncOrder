package ymyoo.infra.messaging.queue.impl;

import ymyoo.infra.messaging.queue.Message;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by 유영모 on 2016-11-15.
 */
public class RequestBlockingQueue {

    private static RequestBlockingQueue queue = null;

    private static BlockingQueue<Message> blockingQueue = new ArrayBlockingQueue<>(10);

    private RequestBlockingQueue() {
    }

    public static RequestBlockingQueue getInstance() {
        if(queue == null) {
            return new RequestBlockingQueue();

        } else {
            return queue;
        }
    }

    public void put(Message msg) {
        try {
            blockingQueue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Message take() {
        try {
            return blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}

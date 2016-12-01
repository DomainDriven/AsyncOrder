package ymyoo.infra.messaging.remote.channel;

import ymyoo.infra.messaging.remote.channel.blockingqueue.RequestBlockingQueue;
import ymyoo.infra.messaging.remote.channel.message.Message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by 유영모 on 2016-11-16.
 */
public class Replier implements Runnable {

    @Override
    public void run() {
        BlockingQueue<Message> queue = RequestBlockingQueue.getBlockingQueue();
        ContentBasedRouter router = new ContentBasedRouter();

        while (!Thread.currentThread().isInterrupted()) {
            try {
                // 메시지 수신
                Message message = queue.poll(10, TimeUnit.MILLISECONDS);
                if(message == null) {
                    continue;
                } else {
                    // 메시지 라우팅
                    router.route(message);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

    }
}

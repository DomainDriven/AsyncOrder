package ymyoo.infra.messaging.remote.queue;

import ymyoo.inventory.InventoryService;
import ymyoo.order.domain.OrderItem;
import ymyoo.infra.messaging.remote.queue.blockingqueue.ReplyBlockingQueue;
import ymyoo.infra.messaging.remote.queue.blockingqueue.RequestBlockingQueue;

import java.util.concurrent.BlockingQueue;

/**
 * Created by 유영모 on 2016-11-16.
 */
public class Replier implements Runnable {
    @Override
    public void run() {
        BlockingQueue<Message> queue = RequestBlockingQueue.getBlockingQueue();
        while(true) {
            try {
                Thread.sleep(1000);
                RequestMessage msg = (RequestMessage) queue.take();
                if(msg.getType() == RequestMessage.MessageType.CHECK_INVENTOY) {
                    InventoryService inventoryService = new InventoryService();
                    inventoryService.checkAndReserve((OrderItem)msg.getBody());

                    MessageProducer messageProducer = new MessageProducer(ReplyBlockingQueue.getBlockingQueue());
                    ReplyMessage replyMsg = new ReplyMessage(msg.getId(), null, ReplyMessage.ReplyMessageStatus.SUCCESS);
                    messageProducer.send(replyMsg);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

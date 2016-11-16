package ymyoo.order.infra.messaging.queue;

import ymyoo.inventory.InventoryService;
import ymyoo.order.domain.OrderItem;
import ymyoo.order.infra.messaging.queue.blockingqueue.ReplyBlockingQueue;
import ymyoo.order.infra.messaging.queue.blockingqueue.RequestBlockingQueue;

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
                Message msg = queue.take();
                if(msg.getType() == MessageType.CHECK_INVENTOY) {
                    InventoryService inventoryService = new InventoryService();
                    inventoryService.checkAndReserve((OrderItem)msg.getObjectProperty());

                    MessageProducer messageProducer = new MessageProducer(ReplyBlockingQueue.getBlockingQueue());
                    Message replyMsg = new Message(msg.getId());
                    messageProducer.send(replyMsg);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

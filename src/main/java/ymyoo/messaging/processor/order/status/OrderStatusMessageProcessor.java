package ymyoo.messaging.processor.order.status;

import ymyoo.messaging.core.Message;
import ymyoo.messaging.core.MessageConsumer;

import java.util.List;

/**
 * Created by 유영모 on 2017-01-12.
 */
public class OrderStatusMessageProcessor implements Runnable {
    private final String channel;
    private OrderStatusRepository repository;

    public OrderStatusMessageProcessor(String channel, OrderStatusRepository repository) {
        this.channel = channel;
        this.repository = repository;
    }

    @Override
    public void run() {
        MessageConsumer messageConsumer = new MessageConsumer(channel);
        try {
            while (!Thread.currentThread().isInterrupted()) {
                List<Message> messages = messageConsumer.poll();
                for(Message message : messages) {
                    System.out.println(message);
                    final String orderId = message.getId();
                    final OrderStatusEntity.Status orderStatus = OrderStatusEntity.Status.valueOf(message.getBody());

                    repository.add(new OrderStatusEntity(orderId, orderStatus));
                }
            }
        } finally {
            messageConsumer.close();
        }
    }
}

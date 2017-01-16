package ymyoo.messaging.processor.order.status;

import com.google.gson.Gson;
import ymyoo.app.order.domain.OrderStatus;
import ymyoo.messaging.core.Message;
import ymyoo.messaging.core.MessageConsumer;

import java.util.Date;
import java.util.List;

/**
 * Created by 유영모 on 2017-01-12.
 */
public class OrderStatusMessageProcessor implements Runnable {
    private final String channel;
    private OrderStatusEntityRepository repository;

    public OrderStatusMessageProcessor(String channel, OrderStatusEntityRepository repository) {
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
                    System.out.println("OrderStatusMessageProcessor : "  + message.getBody());

                    OrderStatusEntity orderStatus = new Gson().fromJson(message.getBody(), OrderStatusEntity.class);
                    OrderStatusHistory history = new OrderStatusHistory();
                    history.setStatus(orderStatus.getStatus());
                    history.setCreatedDate(new Date());
                    orderStatus.addHistory(history);

                    repository.add(orderStatus);
                }
            }
        } finally {
            messageConsumer.close();
        }
    }
}

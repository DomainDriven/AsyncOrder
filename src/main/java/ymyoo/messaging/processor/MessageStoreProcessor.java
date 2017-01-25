package ymyoo.messaging.processor;

import com.google.gson.Gson;
import ymyoo.messaging.core.Message;
import ymyoo.messaging.core.EventDrivenMessageConsumer;
import ymyoo.messaging.processor.entitiy.OrderStatusEntity;
import ymyoo.messaging.processor.repository.OrderStatusEntityRepository;
import ymyoo.messaging.processor.entitiy.OrderStatusHistory;

import java.util.Date;
import java.util.List;

/**
 * Created by 유영모 on 2017-01-12.
 */
public class MessageStoreProcessor implements Runnable {
    private final String channel;
    private OrderStatusEntityRepository repository;

    public MessageStoreProcessor(String channel, OrderStatusEntityRepository repository) {
        this.channel = channel;
        this.repository = repository;
    }

    @Override
    public void run() {
        EventDrivenMessageConsumer eventDrivenMessageConsumer = new EventDrivenMessageConsumer(channel);
        try {
            while (!Thread.currentThread().isInterrupted()) {
                List<Message> messages = eventDrivenMessageConsumer.poll();
                for(Message message : messages) {
                    OrderStatusEntity orderStatus = new Gson().fromJson(message.getBody(), OrderStatusEntity.class);
                    OrderStatusHistory history = new OrderStatusHistory();
                    history.setStatus(orderStatus.getStatus());
                    history.setCreatedDate(new Date());
                    orderStatus.addHistory(history);

                    repository.add(orderStatus);
                }
            }
        } finally {
            eventDrivenMessageConsumer.close();
        }
    }
}

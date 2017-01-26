package ymyoo.messaging.processor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ymyoo.messaging.core.EventDrivenMessageConsumer;
import ymyoo.messaging.core.Message;
import ymyoo.messaging.core.MessageChannels;
import ymyoo.messaging.processor.deserializer.IncompleteBusinessActivityDeserializer;
import ymyoo.messaging.processor.deserializer.OrderStatusEntityDeserializer;
import ymyoo.messaging.processor.entitiy.IncompleteBusinessActivity;
import ymyoo.messaging.processor.entitiy.OrderStatusEntity;
import ymyoo.messaging.processor.entitiy.OrderStatusHistory;
import ymyoo.messaging.processor.repository.IncompleteBusinessActivityRepository;
import ymyoo.messaging.processor.repository.OrderStatusEntityRepository;

import javax.persistence.EntityManagerFactory;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 유영모 on 2017-01-12.
 */
public class MessageStoreProcessor implements Runnable {
    private final String channel;
    private OrderStatusEntityRepository orderStatusRepository;
    private IncompleteBusinessActivityRepository incompleteBusinessActivityRepository;

    public MessageStoreProcessor(String channel, EntityManagerFactory emf) {
        this.channel = channel;
        this.orderStatusRepository = new OrderStatusEntityRepository(emf);
        this.incompleteBusinessActivityRepository = new IncompleteBusinessActivityRepository(emf);
    }

    @Override
    public void run() {
        EventDrivenMessageConsumer eventDrivenMessageConsumer = new EventDrivenMessageConsumer(channel);
        try {
            while (!Thread.currentThread().isInterrupted()) {
                List<Message> messages = eventDrivenMessageConsumer.poll();
                for(Message message : messages) {
                    Type type = new TypeToken<HashMap<String, Object>>(){}.getType();
                    Map<String, String> content = new Gson().fromJson(message.getBody(), type);

                    if(content.get("type").equals(MessageChannels.MESSAGE_STORE_TYPE_ORDER_STATUS)) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.registerTypeAdapter(OrderStatusEntity.class, new OrderStatusEntityDeserializer());
                        Gson gson = gsonBuilder.create();
                        OrderStatusEntity orderStatus = gson.fromJson(message.getBody(), OrderStatusEntity.class);

                        OrderStatusHistory history = new OrderStatusHistory();
                        history.setStatus(orderStatus.getStatus());
                        history.setCreatedDate(new Date());
                        orderStatus.addHistory(history);
                        orderStatusRepository.add(orderStatus);
                    } else if(content.get("type").equals(MessageChannels.MESSAGE_STORE_TYPE_INCOMPLETE_BUSINESS_ACTIVITY)) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.registerTypeAdapter(IncompleteBusinessActivity.class, new IncompleteBusinessActivityDeserializer());
                        Gson gson = gsonBuilder.create();

                        IncompleteBusinessActivity incompleteBusinessActivity = gson.fromJson(message.getBody(), IncompleteBusinessActivity.class);
                        incompleteBusinessActivityRepository.add(incompleteBusinessActivity);
                    }

                }
            }
        } finally {
            eventDrivenMessageConsumer.close();
        }
    }
}

package ymyoo.infra.messaging.processor;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import ymyoo.infra.messaging.core.EventDrivenMessageConsumer;
import ymyoo.infra.messaging.core.MessageChannels;
import ymyoo.infra.messaging.core.Message;
import ymyoo.infra.messaging.processor.entitiy.IncompleteBusinessActivity;
import ymyoo.infra.messaging.processor.entitiy.OrderStatusEntity;
import ymyoo.infra.messaging.processor.entitiy.OrderStatusHistory;
import ymyoo.infra.messaging.processor.repository.IncompleteBusinessActivityRepository;
import ymyoo.infra.messaging.processor.repository.OrderStatusEntityRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 유영모 on 2017-01-12.
 */
public class MessageStoreProcessor implements Runnable {
    private final String channel;
    private OrderStatusEntityRepository orderStatusRepository;
    private IncompleteBusinessActivityRepository incompleteBusinessActivityRepository;

    public MessageStoreProcessor(String channel) {
        this.channel = channel;
        this.orderStatusRepository = new OrderStatusEntityRepository();
        this.incompleteBusinessActivityRepository = new IncompleteBusinessActivityRepository();
    }

    @Override
    public void run() {
        EventDrivenMessageConsumer eventDrivenMessageConsumer = new EventDrivenMessageConsumer(channel);
        try {
            while (!Thread.currentThread().isInterrupted()) {
                List<Message> messages = eventDrivenMessageConsumer.poll();
                for(Message message : messages) {
                    String messageType = message.getHeaders().get("type");
                    if(messageType.equals(MessageChannels.MESSAGE_STORE_TYPE_ORDER_STATUS)) {
                        OrderStatusEntity orderStatus = new OrderStatusEntity();

                        try {
                            BeanUtilsBean beanUtilsBean = new BeanUtilsBean(new ConvertUtilsBean() {
                                @Override
                                public Object convert(String value, Class clazz) {
                                    if (clazz.isEnum()){
                                        return Enum.valueOf(clazz, value);
                                    }else{
                                        return super.convert(value, clazz);
                                    }
                                }
                            });
                            beanUtilsBean.populate(orderStatus, (Map)message.getBody());

                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }

                        OrderStatusHistory history = new OrderStatusHistory();
                        history.setStatus(orderStatus.getStatus());
                        history.setCreatedDate(new Date());
                        orderStatus.addHistory(history);
                        orderStatusRepository.add(orderStatus);
                    } else if(messageType.equals(MessageChannels.MESSAGE_STORE_TYPE_INCOMPLETE_BUSINESS_ACTIVITY)) {
                        IncompleteBusinessActivity activity = new IncompleteBusinessActivity();
                        try {
                            BeanUtils.populate(activity, (Map)message.getBody());
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        incompleteBusinessActivityRepository.add(activity);
                    }

                }
            }
        } finally {
            eventDrivenMessageConsumer.close();
        }
    }
}

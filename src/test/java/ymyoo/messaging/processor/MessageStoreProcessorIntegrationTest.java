package ymyoo.messaging.processor;

import com.google.gson.Gson;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import ymyoo.app.order.domain.OrderStatus;
import ymyoo.messaging.core.KafkaIntegrationTest;
import ymyoo.messaging.core.MessageChannels;
import ymyoo.messaging.processor.entitiy.IncompleteBusinessActivity;
import ymyoo.messaging.processor.entitiy.OrderStatusEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 유영모 on 2017-01-12.
 */
public class MessageStoreProcessorIntegrationTest extends KafkaIntegrationTest {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("order");

    @AfterClass
    public static void tearDownAfterClass() {
        emf.close();
    }

    @Test
    public void run_메시지_type이_ORDER_STATUS_인경우() {
        // given
        final String channel = MessageChannels.MESSAGE_STORE;
        final String messageId = generateId();

        String orderId = generateId();
        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("type", "ORDER-STATUS");
        messageBody.put("message", new OrderStatus(orderId, OrderStatus.Status.INVENTORY_CHECKED));
        sendMessage(channel, messageId, new Gson().toJson(messageBody));

        // when
        Thread orderStatusMessageProcessor = new Thread(new MessageStoreProcessor(channel, emf));
        orderStatusMessageProcessor.start();

        waitCurrentThread(5);

        // then
        OrderStatusEntity actual = getOrderStatus(orderId);
        Assert.assertEquals(orderId, actual.getOrderId());
        Assert.assertEquals(OrderStatusEntity.Status.INVENTORY_CHECKED, actual.getStatus());
    }

    private OrderStatusEntity getOrderStatus(String orderId) {
        EntityManager em = emf.createEntityManager();
        OrderStatusEntity orderStatus = em.find(OrderStatusEntity.class, orderId);
        em.close();
        return orderStatus;
    }

    @Test
    public void run_메시지_type이_INCOMPLETE_BUSINESS_ACTIVITY_인_경우() {
        // given
        final String channel = MessageChannels.MESSAGE_STORE;
        final String messageId = generateId();

        String orderId = generateId();
        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("type", "INCOMPLETE-BUSINESS-ACTIVITY");
        IncompleteBusinessActivity incompleteBusinessActivity = new IncompleteBusinessActivity(orderId, "SENDING_CELL_PHONE");
        messageBody.put("message", incompleteBusinessActivity);
        sendMessage(channel, messageId, new Gson().toJson(messageBody));

        // when
        Thread orderStatusMessageProcessor = new Thread(new MessageStoreProcessor(channel, emf));
        orderStatusMessageProcessor.start();

        waitCurrentThread(5);

        // then
        EntityManager em = emf.createEntityManager();

        TypedQuery<IncompleteBusinessActivity> query =
                em.createQuery("select iba from IncompleteBusinessActivity iba where iba.orderId = :orderId"
                        ,IncompleteBusinessActivity.class);
        query.setParameter("orderId", orderId);

        List<IncompleteBusinessActivity> actual = query.getResultList();

        Assert.assertEquals(1, actual.size());
        Assert.assertEquals(incompleteBusinessActivity.getOrderId(), actual.get(0).getOrderId());
        Assert.assertEquals(incompleteBusinessActivity.getActivity(), actual.get(0).getActivity());

        em.close();
    }
}
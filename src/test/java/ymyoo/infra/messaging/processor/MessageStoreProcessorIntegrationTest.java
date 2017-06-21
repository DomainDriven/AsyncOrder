package ymyoo.infra.messaging.processor;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import ymyoo.infra.messaging.core.KafkaIntegrationTest;
import ymyoo.infra.messaging.core.MessageChannels;
import ymyoo.infra.messaging.core.Message;
import ymyoo.infra.messaging.processor.entitiy.IncompleteBusinessActivity;
import ymyoo.infra.messaging.processor.entitiy.OrderStatusEntity;
import ymyoo.infra.persistence.GlobalEntityManagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 유영모 on 2017-01-12.
 */
public class MessageStoreProcessorIntegrationTest extends KafkaIntegrationTest {
    @AfterClass
    public static void tearDownAfterClass() {
        GlobalEntityManagerFactory.closeEntityManagerFactory();
    }

    @Test
    public void run_메시지_type이_ORDER_STATUS_인경우() {
        // given
        final String channel = MessageChannels.MESSAGE_STORE;
        final String messageId = generateId();
        Map<String, String> messageHeader = new HashMap<>();
        messageHeader.put("type", MessageChannels.MESSAGE_STORE_TYPE_ORDER_STATUS);

        String orderId = generateId();
        OrderStatusEntity orderStatus = new OrderStatusEntity(orderId, OrderStatusEntity.Status.INVENTORY_CHECKED);

        // when
        sendMessage(channel, new Message(messageId, messageHeader, orderStatus));
        Thread orderStatusMessageProcessor = new Thread(new MessageStoreProcessor(channel));
        orderStatusMessageProcessor.start();

        waitCurrentThread(5);

        // then
        OrderStatusEntity actual = getOrderStatus(orderId);
        Assert.assertEquals(orderId, actual.getOrderId());
        Assert.assertEquals(OrderStatusEntity.Status.INVENTORY_CHECKED, actual.getStatus());
    }

    private OrderStatusEntity getOrderStatus(String orderId) {
        EntityManager em = GlobalEntityManagerFactory.getEntityManagerFactory().createEntityManager();
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
        Map<String, String> messageHeader = new HashMap<>();
        messageHeader.put("type", MessageChannels.MESSAGE_STORE_TYPE_INCOMPLETE_BUSINESS_ACTIVITY);;
        IncompleteBusinessActivity incompleteBusinessActivity = new IncompleteBusinessActivity(orderId, "SENDING_CELL_PHONE");
        sendMessage(channel, new Message(messageId, messageHeader, incompleteBusinessActivity));

        // when
        Thread orderStatusMessageProcessor = new Thread(new MessageStoreProcessor(channel));
        orderStatusMessageProcessor.start();

        waitCurrentThread(5);

        // then
        EntityManager em = GlobalEntityManagerFactory.getEntityManagerFactory().createEntityManager();

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
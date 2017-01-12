package ymyoo.messaging.processor;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import ymyoo.messaging.core.KafkaIntegrationTest;
import ymyoo.messaging.core.MessageChannels;
import ymyoo.messaging.processor.order.status.OrderStatusEntity;
import ymyoo.messaging.processor.order.status.OrderStatusMessageProcessor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by 유영모 on 2017-01-12.
 */
public class OrderStatusMessageProcessorTest extends KafkaIntegrationTest {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("order");

    @AfterClass
    public static void tearDownAfterClass() {
        emf.close();
    }

    @Test
    public void run() {
        // given
        final String channel = MessageChannels.LOG_ORDER_STATUS;
        final String messageId = generateId();
        final String messageBody = "SALE_ORDER_CREATED";
        sendMessage(channel, messageId, messageBody);

        // when
        Thread orderStatusMessageProcessor = new Thread(new OrderStatusMessageProcessor());
        orderStatusMessageProcessor.start();

        waitCurrentThread(5);

        // then
        OrderStatusEntity orderStatus = getOrderStatus(messageId);
        Assert.assertEquals(messageId, orderStatus.getOrderId());
        Assert.assertEquals(OrderStatusEntity.Status.SALE_ORDER_CREATED, orderStatus.getStatus());
    }

    private OrderStatusEntity getOrderStatus(String messageId) {
        EntityManager em = emf.createEntityManager();
        OrderStatusEntity orderStatus = em.find(OrderStatusEntity.class, messageId);
        em.close();
        return orderStatus;
    }
}
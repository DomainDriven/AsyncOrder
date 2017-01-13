package ymyoo.messaging.processor.order.status;

import com.google.gson.Gson;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import ymyoo.app.order.domain.OrderStatus;
import ymyoo.messaging.core.KafkaIntegrationTest;
import ymyoo.messaging.core.MessageChannels;

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

        String orderId = generateId();
        OrderStatus orderStatus = new OrderStatus(orderId, OrderStatus.Status.INVENTORY_CHECKED);
        sendMessage(channel, messageId, new Gson().toJson(orderStatus));

        // when
        OrderStatusEntityRepository repository = new OrderStatusEntityRepository(emf);
        Thread orderStatusMessageProcessor = new Thread(new OrderStatusMessageProcessor(channel, repository));
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
}
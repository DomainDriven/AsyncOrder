package ymyoo.messaging.processor.order.status;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

/**
 * Created by 유영모 on 2017-01-13.
 */
public class OrderStatusEntityRepositoryTest {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("order");

    @AfterClass
    public static void tearDownAfterClass() {
        emf.close();
    }

    @Test
    public void add() throws Exception {
        // given
        String orderId = java.util.UUID.randomUUID().toString().toUpperCase();
        OrderStatusEntity.Status status = OrderStatusEntity.Status.INVENTORY_CHECKED;

        OrderStatusEntity orderStatusEntity = new OrderStatusEntity(orderId, status);
        orderStatusEntity.addHistory(new OrderStatusHistory(new OrderStatusHistoryId(orderId, status), new Date()));

        // when
        OrderStatusEntityRepository repository = new OrderStatusEntityRepository(emf);
        repository.add(orderStatusEntity);

        // then
        EntityManager em = emf.createEntityManager();
        OrderStatusEntity actual = em.find(OrderStatusEntity.class, orderId);
        em.close();

        Assert.assertEquals(orderId, actual.getOrderId());
        Assert.assertEquals(status, actual.getStatus());
        Assert.assertEquals(1, actual.getHistories().size());
        Assert.assertEquals(orderId, actual.getHistories().get(0).getId().getOrderId());
        Assert.assertEquals(status, actual.getHistories().get(0).getId().getStatus());
        Assert.assertNotNull(actual.getHistories().get(0).getCreatedDate());
    }

}
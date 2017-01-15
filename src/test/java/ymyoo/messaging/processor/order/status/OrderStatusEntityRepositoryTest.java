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

        OrderStatusHistory history = new OrderStatusHistory();
        history.setStatus(status);
        history.setCreatedDate(new Date());

        OrderStatusEntity orderStatusEntity = new OrderStatusEntity();
        orderStatusEntity.setOrderId(orderId);
        orderStatusEntity.setStatus(status);
        orderStatusEntity.addHistory(history);


        // when
        OrderStatusEntityRepository repository = new OrderStatusEntityRepository(emf);
        repository.add(orderStatusEntity);

        // then
        EntityManager em = emf.createEntityManager();
        OrderStatusEntity actual = em.find(OrderStatusEntity.class, orderId);


        Assert.assertEquals(orderId, actual.getOrderId());
        Assert.assertEquals(status, actual.getStatus());
        Assert.assertEquals(1, actual.getHistories().size());
        Assert.assertEquals(status, actual.getHistories().get(0).getStatus());
        Assert.assertNotNull(actual.getHistories().get(0).getCreatedDate());

        em.close();
    }

}
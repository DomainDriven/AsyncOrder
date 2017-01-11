package ymyoo.app.order.infrastructure.repository;

import org.junit.Assert;
import org.junit.Test;
import ymyoo.app.order.domain.OrderStatus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by 유영모 on 2017-01-11.
 */
public class OrderStatusRepositoryTest {

    @Test
    public void find() throws Exception {
        // given
        String orderId = java.util.UUID.randomUUID().toString().toUpperCase();
        OrderStatus expectedOrderStatus = new OrderStatus(orderId, OrderStatus.Status.INVENTORY_CHECKED);

        setUpFixture(expectedOrderStatus);

        // when
        OrderStatusRepository repository = new OrderStatusRepository();
        OrderStatus actualOrderStatus = repository.find(orderId);

        // then
        Assert.assertEquals(expectedOrderStatus.getOrderId(), actualOrderStatus.getOrderId());
        Assert.assertEquals(expectedOrderStatus.getStatus(), actualOrderStatus.getStatus());
    }

    private void setUpFixture(OrderStatus orderStatus) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("order");

        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(orderStatus);

        transaction.commit();
        em.close();

        emf.close();
    }

}
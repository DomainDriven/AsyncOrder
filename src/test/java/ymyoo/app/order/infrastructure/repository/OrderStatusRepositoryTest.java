package ymyoo.app.order.infrastructure.repository;

import org.junit.*;
import ymyoo.app.order.domain.OrderStatus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by 유영모 on 2017-01-11.
 */
public class OrderStatusRepositoryTest {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("order");
    String orderId = java.util.UUID.randomUUID().toString().toUpperCase();

    @AfterClass
    public static void tearDownAfterClass() {
        emf.close();
    }

    @Before
    public void setUp() throws Exception {
        OrderStatus fixtureOrderStatus = getOrderStatusTestFixture();

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(fixtureOrderStatus);

        transaction.commit();
        em.close();
    }

    @After
    public void tearDown() throws Exception {
        EntityManager em = emf.createEntityManager();

        OrderStatus orderStatus = em.find(OrderStatus.class, orderId);

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.remove(orderStatus);

        transaction.commit();
        em.close();
    }

    private OrderStatus getOrderStatusTestFixture() {
        return new OrderStatus(orderId, OrderStatus.Status.INVENTORY_CHECKED);
    }

    @Test
    public void find() throws Exception {
        // given
        OrderStatus expectedOrderStatus = new OrderStatus(orderId, OrderStatus.Status.INVENTORY_CHECKED);

        // when
        OrderStatusRepository repository = new OrderStatusRepository(emf);
        OrderStatus actualOrderStatus = repository.find(orderId);

        // then
        Assert.assertEquals(expectedOrderStatus.getOrderId(), actualOrderStatus.getOrderId());
        Assert.assertEquals(expectedOrderStatus.getStatus(), actualOrderStatus.getStatus());
    }


}
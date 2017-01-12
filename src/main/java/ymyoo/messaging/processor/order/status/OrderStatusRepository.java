package ymyoo.messaging.processor.order.status;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by 유영모 on 2017-01-11.
 */
public class OrderStatusRepository {
    private EntityManagerFactory emf;

    public OrderStatusRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public OrderStatusEntity find(final String orderId) {
        EntityManager em = emf.createEntityManager();
        OrderStatusEntity orderStatus = em.find(OrderStatusEntity.class, orderId);
        em.close();

        return orderStatus;
    }
}

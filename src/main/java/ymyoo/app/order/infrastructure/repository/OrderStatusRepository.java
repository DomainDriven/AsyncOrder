package ymyoo.app.order.infrastructure.repository;

import ymyoo.app.order.domain.OrderStatus;

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

    public OrderStatus find(final String orderId) {
        EntityManager em = emf.createEntityManager();
        OrderStatus orderStatus = em.find(OrderStatus.class, orderId);
        em.close();

        return orderStatus;
    }
}

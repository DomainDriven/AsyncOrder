package ymyoo.app.order.infrastructure.repository;

import ymyoo.app.order.domain.OrderStatus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by 유영모 on 2017-01-11.
 */
public class OrderStatusRepository {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("order");

    public OrderStatus find(final String orderId) {
        EntityManager em = emf.createEntityManager();
        return em.find(OrderStatus.class, orderId);
    }
}

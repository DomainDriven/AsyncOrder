package ymyoo.app.order.infrastructure.repository;

import ymyoo.app.order.domain.OrderStatus;
import ymyoo.persistence.GlobalEntityManagerFactory;

import javax.persistence.EntityManager;

/**
 * Created by 유영모 on 2017-01-11.
 */
public class OrderStatusRepository {
    public OrderStatus find(final String orderId) {
        EntityManager em = GlobalEntityManagerFactory.getEntityManagerFactory().createEntityManager();
        OrderStatus orderStatus = em.find(OrderStatus.class, orderId);
        em.close();

        return orderStatus;
    }
}

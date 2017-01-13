package ymyoo.messaging.processor.order.status;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * Created by 유영모 on 2017-01-11.
 */
public class OrderStatusRepository {
    private EntityManagerFactory emf;

    public OrderStatusRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void add(OrderStatusEntity aOrderStatus) {
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(aOrderStatus);

        transaction.commit();
        em.close();
    }
}

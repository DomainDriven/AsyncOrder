package ymyoo.messaging.processor.order.status;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * Created by 유영모 on 2017-01-11.
 */
public class OrderStatusEntityRepository {
    private EntityManagerFactory emf;

    public OrderStatusEntityRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void add(OrderStatusEntity aOrderStatus) {
        EntityManager em = emf.createEntityManager();

        OrderStatusEntity find = em.find(OrderStatusEntity.class, aOrderStatus.getOrderId());

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        if(find == null) {
            em.persist(aOrderStatus);
        } else {
            find.setStatus(aOrderStatus.getStatus());
            for(OrderStatusHistory history : aOrderStatus.getHistories()) {
                find.addHistory(history);
            }
        }

        transaction.commit();
        em.close();
    }
}

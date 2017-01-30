package ymyoo.messaging.processor.repository;

import ymyoo.messaging.processor.entitiy.OrderStatusEntity;
import ymyoo.messaging.processor.entitiy.OrderStatusHistory;
import ymyoo.persistence.GlobalEntityManagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * Created by 유영모 on 2017-01-11.
 */
public class OrderStatusEntityRepository {

    public void add(OrderStatusEntity aOrderStatus) {
        EntityManagerFactory emf = GlobalEntityManagerFactory.getEntityManagerFactory();
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

package ymyoo.messaging.processor.repository;

import ymyoo.messaging.processor.entitiy.IncompleteBusinessActivity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * Created by 유영모 on 2017-01-25.
 */
public class IncompleteBusinessActivityRepository {
    private EntityManagerFactory emf;

    public IncompleteBusinessActivityRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void add(IncompleteBusinessActivity incompleteBusinessActivity) {
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(incompleteBusinessActivity);

        transaction.commit();
        em.close();
    }
}

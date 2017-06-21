package ymyoo.infra.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * Created by 유영모 on 2017-02-14.
 */
public class TransactionJpaTemplate {
    public EntityManagerFactory emf;

    public TransactionJpaTemplate(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public < T > T execute(ABlockOfCode < T > aBlockOfCode) {
        EntityManager em = this.emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            T returnValue = aBlockOfCode.execute(em);
            tx.commit();
            return returnValue;
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }
}

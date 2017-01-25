package ymyoo.messaging.processor.repository;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import ymyoo.messaging.processor.entitiy.IncompleteBusinessActivity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by 유영모 on 2017-01-25.
 */
public class IncompleteBusinessActivityRepositoryTest {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("order");

    @AfterClass
    public static void tearDownAfterClass() {
        emf.close();
    }

    @Test
    public void add() throws Exception {
        // given
        String orderId = java.util.UUID.randomUUID().toString().toUpperCase();
        String activity = "SENDING_CELL_PHONE";
        IncompleteBusinessActivity incompleteBusinessActivity = new IncompleteBusinessActivity(orderId, activity);

        // when
        IncompleteBusinessActivityRepository repository = new IncompleteBusinessActivityRepository(emf);
        repository.add(incompleteBusinessActivity);

        // then
        EntityManager em = emf.createEntityManager();

        TypedQuery<IncompleteBusinessActivity> query =
                em.createQuery("select iba from IncompleteBusinessActivity iba where iba.orderId = :orderId"
                        ,IncompleteBusinessActivity.class);
        query.setParameter("orderId", orderId);

        List<IncompleteBusinessActivity> actual = query.getResultList();

        Assert.assertEquals(1, actual.size());
        Assert.assertEquals(incompleteBusinessActivity.getOrderId(), actual.get(0).getOrderId());
        Assert.assertEquals(incompleteBusinessActivity.getActivity(), actual.get(0).getActivity());

        em.close();
    }
}
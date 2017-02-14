package ymyoo.messaging.processor.repository;

import ymyoo.messaging.processor.entitiy.IncompleteBusinessActivity;
import ymyoo.persistence.TransactionJpaTemplate;

/**
 * Created by 유영모 on 2017-01-25.
 */
public class IncompleteBusinessActivityRepository {
    public void add(IncompleteBusinessActivity incompleteBusinessActivity) {
        TransactionJpaTemplate transactionTemplate = new TransactionJpaTemplate();

        transactionTemplate.execute((em) -> {
            em.persist(incompleteBusinessActivity);
            return null;
        });
    }
}

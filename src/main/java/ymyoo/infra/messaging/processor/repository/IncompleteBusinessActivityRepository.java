package ymyoo.infra.messaging.processor.repository;

import ymyoo.infra.messaging.processor.entitiy.IncompleteBusinessActivity;
import ymyoo.infra.persistence.TransactionJpaTemplate;

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

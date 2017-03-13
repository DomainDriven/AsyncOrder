package ymyoo.app.order.infrastructure.repository;

import ymyoo.app.order.domain.command.po.PurchaseOrder;
import ymyoo.persistence.TransactionJpaTemplate;

/**
 * Created by 유영모 on 2017-03-13.
 */
public class PurchaseOrderRepository {

    public void add(final PurchaseOrder po) {
        TransactionJpaTemplate jpaTemplate = new TransactionJpaTemplate();

        jpaTemplate.execute((em)-> {
            em.persist(po);
            return null;
        });
    }

}

package ymyoo.app.order.infrastructure.repository;

import ymyoo.app.order.domain.command.po.PurchaseOrder;

/**
 * Created by 유영모 on 2017-03-13.
 */
public class PurchaseOrderRepository extends AbstractOrderJpaRepository {
    public void add(final PurchaseOrder po) {
        jpaTemplate.execute((em)-> {
            em.persist(po);
            return null;
        });
    }
}

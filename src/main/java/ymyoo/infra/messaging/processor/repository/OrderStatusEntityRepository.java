package ymyoo.infra.messaging.processor.repository;

import ymyoo.infra.messaging.processor.entitiy.OrderStatusEntity;
import ymyoo.infra.messaging.processor.entitiy.OrderStatusHistory;
import ymyoo.infra.persistence.TransactionJpaTemplate;

/**
 * Created by 유영모 on 2017-01-11.
 */
public class OrderStatusEntityRepository {

    public void add(OrderStatusEntity aOrderStatus) {
        TransactionJpaTemplate transactionTemplate = new TransactionJpaTemplate();

        transactionTemplate.execute((em) -> {
            OrderStatusEntity find = em.find(OrderStatusEntity.class, aOrderStatus.getOrderId());
            if(find == null) {
                em.persist(aOrderStatus);
            } else {
                find.setStatus(aOrderStatus.getStatus());
                for(OrderStatusHistory history : aOrderStatus.getHistories()) {
                    find.addHistory(history);
                }
            }
            return null;
        });
    }
}

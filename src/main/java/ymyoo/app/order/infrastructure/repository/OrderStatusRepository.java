package ymyoo.app.order.infrastructure.repository;

import ymyoo.app.order.domain.command.OrderStatus;
import ymyoo.infra.persistence.TransactionJpaTemplate;

/**
 * Created by 유영모 on 2017-01-11.
 */
public class OrderStatusRepository {
    public OrderStatus find(final String orderId) {
        TransactionJpaTemplate jpaTemplate = new TransactionJpaTemplate();

        return jpaTemplate.execute((em)-> {
            return em.find(OrderStatus.class, orderId);
        });
    }
}

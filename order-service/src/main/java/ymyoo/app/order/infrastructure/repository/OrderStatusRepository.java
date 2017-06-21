package ymyoo.app.order.infrastructure.repository;


import ymyoo.app.order.domain.command.OrderStatus;

/**
 * Created by 유영모 on 2017-01-11.
 */
public class OrderStatusRepository extends AbstractOrderJpaRepository {

    public OrderStatus find(final String orderId) {
        return jpaTemplate.execute((em)-> {
            return em.find(OrderStatus.class, orderId);
        });
    }
}

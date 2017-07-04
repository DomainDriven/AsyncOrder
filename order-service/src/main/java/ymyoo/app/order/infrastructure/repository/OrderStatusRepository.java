package ymyoo.app.order.infrastructure.repository;


import ymyoo.app.order.domain.command.OrderStatus;
import ymyoo.app.order.domain.command.OrderStatusHistory;

/**
 * Created by 유영모 on 2017-01-11.
 */
public class OrderStatusRepository extends AbstractOrderJpaRepository {

    public OrderStatus find(final String orderId) {
        return jpaTemplate.execute((em)-> {
            return em.find(OrderStatus.class, orderId);
        });
    }

    public void add(OrderStatus aOrderStatus) {
        jpaTemplate.execute((em) -> {
            OrderStatus find = em.find(OrderStatus.class, aOrderStatus.getOrderId());

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

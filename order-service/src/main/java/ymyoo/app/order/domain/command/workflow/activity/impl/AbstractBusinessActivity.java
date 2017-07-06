package ymyoo.app.order.domain.command.workflow.activity.impl;

import ymyoo.app.order.domain.command.OrderStatus;
import ymyoo.app.order.domain.command.OrderStatusHistory;
import ymyoo.app.order.domain.command.workflow.activity.BusinessActivity;
import ymyoo.app.order.infrastructure.repository.OrderStatusRepository;

import java.util.Date;

/**
 * Created by yooyoung-mo on 2017. 7. 6..
 */
public abstract class AbstractBusinessActivity<T, R>  implements BusinessActivity<T, R> {
    private OrderStatus beforePerformActivityStatus;
    private OrderStatus afterPerformActivityStatus;


    public AbstractBusinessActivity(final String orderId, OrderStatus.Status beforePerformActivityStatus,
                                    OrderStatus.Status afterPerformActivityStatus) {


        this.beforePerformActivityStatus = new OrderStatus(orderId, beforePerformActivityStatus);
        this.afterPerformActivityStatus = new OrderStatus(orderId, afterPerformActivityStatus);
    }

    private void beforePerform() {
        persistOrderStatus(beforePerformActivityStatus);
    }


    private void afterPerform() {
        persistOrderStatus(afterPerformActivityStatus);
    }

    private void persistOrderStatus(OrderStatus status) {
        OrderStatusRepository orderStatusRepository = new OrderStatusRepository();

        OrderStatusHistory history = new OrderStatusHistory();
        history.setStatus(status.getStatus());
        history.setCreatedDate(new Date());
        status.addHistory(history);

        orderStatusRepository.add(status);
    }

    @Override
    public R perform(T t) {
        beforePerform();

        R r = performActivity(t);

        afterPerform();

        return r;
    }

    public abstract R performActivity(T t);
}

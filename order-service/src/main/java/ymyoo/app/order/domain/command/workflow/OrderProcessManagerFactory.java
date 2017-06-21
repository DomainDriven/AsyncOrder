package ymyoo.app.order.domain.command.workflow;

import ymyoo.app.order.domain.command.Order;

/**
 * Created by 유영모 on 2016-10-26.
 */
public class OrderProcessManagerFactory {

    public static OrderProcessManager create(Order order) {
        return new DefaultOrderProcessManager();
    }
}

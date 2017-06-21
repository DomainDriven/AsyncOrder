package ymyoo.app.order.domain.command.workflow;

import ymyoo.app.order.domain.command.Order;

/**
 * Created by 유영모 on 2016-10-24.
 */
public interface OrderProcessManager {
    void runWorkflow(Order order);
}

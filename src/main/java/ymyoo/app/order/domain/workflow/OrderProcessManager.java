package ymyoo.app.order.domain.workflow;

import ymyoo.app.order.domain.Order;

/**
 * Created by 유영모 on 2016-10-24.
 */
public interface OrderProcessManager {
    void runWorkflow(Order order);
}

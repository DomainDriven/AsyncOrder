package ymyoo.order.domain.workflow;

import ymyoo.order.domain.Order;

/**
 * Created by 유영모 on 2016-10-24.
 */
public interface OrderProcessManager {
    void runWorkflow(Order order);
}

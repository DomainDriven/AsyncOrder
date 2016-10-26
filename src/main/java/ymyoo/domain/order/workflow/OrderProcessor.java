package ymyoo.domain.order.workflow;

import ymyoo.domain.order.Order;

/**
 * Created by 유영모 on 2016-10-24.
 */
public interface OrderProcessor {
    void runWorkflow(Order order);
}

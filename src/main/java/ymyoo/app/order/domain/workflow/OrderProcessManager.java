package ymyoo.app.order.domain.workflow;

import ymyoo.app.order.domain.so.SalesOrder;

/**
 * Created by 유영모 on 2016-10-24.
 */
public interface OrderProcessManager {
    void runWorkflow(SalesOrder salesOrder);
}

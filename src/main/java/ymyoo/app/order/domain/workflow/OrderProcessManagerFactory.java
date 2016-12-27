package ymyoo.app.order.domain.workflow;

import ymyoo.app.order.domain.so.SalesOrder;

/**
 * Created by 유영모 on 2016-10-26.
 */
public class OrderProcessManagerFactory {

    public static OrderProcessManager create(SalesOrder order) {
        return new DefaultOrderProcessManager();
    }
}

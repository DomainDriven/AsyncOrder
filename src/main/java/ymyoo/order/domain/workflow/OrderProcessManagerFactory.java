package ymyoo.order.domain.workflow;

import ymyoo.order.domain.so.SalesOrder;
import ymyoo.order.domain.so.OrderItemDeliveryType;
import ymyoo.order.domain.exception.UnSupportedDeliveryTypeException;

/**
 * Created by 유영모 on 2016-10-26.
 */
public class OrderProcessManagerFactory {

    public static OrderProcessManager create(SalesOrder order) {
        return new DefaultOrderProcessManager();
    }
}

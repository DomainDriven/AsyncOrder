package ymyoo.app.order.service;

import ymyoo.app.order.domain.query.dto.PlacingOrderStatus;

/**
 *
 * Created by yooyoung-mo on 2017. 7. 4..
 */
public interface OrderApplicationService {
    String placeOrder(WorkOrder workOrder);

    PlacingOrderStatus getOrderStatus(String orderId);
}

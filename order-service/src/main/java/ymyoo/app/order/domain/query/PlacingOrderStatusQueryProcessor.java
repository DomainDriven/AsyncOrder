package ymyoo.app.order.domain.query;

import ymyoo.app.order.domain.command.OrderStatus;
import ymyoo.app.order.domain.query.dto.PlacingOrderStatus;
import ymyoo.app.order.infrastructure.repository.OrderStatusRepository;

/**
 * Created by yooyoung-mo on 2017. 7. 4..
 */
public class PlacingOrderStatusQueryProcessor {
    public PlacingOrderStatus getPlacingOrderStatus(String orderId) {
        OrderStatusRepository repository = new OrderStatusRepository();
        OrderStatus orderStatus = repository.find(orderId);

        if(orderStatus == null) {
            return PlacingOrderStatus.ORDER_READY;
        } else {
            return PlacingOrderStatus.valueOf(orderStatus.getStatus().name());
        }

    }
}

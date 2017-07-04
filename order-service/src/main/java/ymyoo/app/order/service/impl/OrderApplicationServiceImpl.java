package ymyoo.app.order.service.impl;

import org.springframework.stereotype.Service;
import ymyoo.app.order.domain.command.*;
import ymyoo.app.order.domain.query.PlacingOrderStatusQueryProcessor;
import ymyoo.app.order.domain.query.dto.PlacingOrderStatus;
import ymyoo.app.order.service.OrderApplicationService;
import ymyoo.app.order.service.WorkOrder;

/**
 * Created by yooyoung-mo on 2017. 7. 4..
 */
@Service
public class OrderApplicationServiceImpl implements OrderApplicationService {
    @Override
    public String placeOrder(WorkOrder workOrder) {
        // 도메인 객체 생성
        Order order = OrderFactory.create(new Orderer(workOrder.getCustomerName(), workOrder.getCustomerCellPhoneNo(), workOrder.getCustomerEmail()),
                new OrderItem(workOrder.getProductId(), workOrder.getOrderQty(), OrderItemDeliveryType.DIRECTING),
                new OrderPayment(workOrder.getPaymentAmt(), workOrder.getPaymentCardNo()));

        // 비동기 주문!
        String orderId = order.placeOrder();

        // 바로 주문 아이디 반환
        return orderId;
    }

    @Override
    public PlacingOrderStatus getOrderStatus(String orderId) {
        PlacingOrderStatusQueryProcessor processor = new PlacingOrderStatusQueryProcessor();

        return processor.getPlacingOrderStatus(orderId);
    }
}

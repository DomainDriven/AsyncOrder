package ymyoo.app.order.domain;

import org.junit.Assert;
import org.junit.Test;
import ymyoo.app.order.domain.command.*;
import ymyoo.app.order.domain.command.workflow.OrderProcessManager;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by 유영모 on 2016-12-15.
 */
public class OrderTest {
    @Test
    public void placeOrder() throws Exception {
        // given
        OrderProcessManager mockOrderProcessManager = mock(OrderProcessManager.class);

        Order order = new Order(new Orderer("유영모", "010-0000-0000", "gigamadness@gmail.com"),
                new OrderItem("P0001", 2, OrderItemDeliveryType.AGENCY),
                new OrderPayment(2000, "123-456-0789"),
                mockOrderProcessManager);

        // when
        String orderId = order.placeOrder();

        // then
        Assert.assertNotNull(orderId);
        verify(mockOrderProcessManager).runWorkflow(order);
    }

}
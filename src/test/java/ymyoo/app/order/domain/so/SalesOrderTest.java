package ymyoo.app.order.domain.so;

import org.junit.Assert;
import org.junit.Test;
import ymyoo.app.order.domain.workflow.OrderProcessManager;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by 유영모 on 2016-12-15.
 */
public class SalesOrderTest {
    @Test
    public void placeOrder() throws Exception {
        // given
        OrderProcessManager mockOrderProcessManager = mock(OrderProcessManager.class);

        SalesOrder salesOrder = new SalesOrder(new Orderer("유영모", "010-0000-0000"),
                new SalesOrderItem("P0001", 2, OrderItemDeliveryType.AGENCY),
                new SalesOrderPayment(2000, "123-456-0789"),
                mockOrderProcessManager);

        // when
        String orderId = salesOrder.placeOrder();

        // then
        Assert.assertNotNull(orderId);
        verify(mockOrderProcessManager).runWorkflow(salesOrder);
    }

}
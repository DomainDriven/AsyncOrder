package ymyoo.app.order.domain.command;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import ymyoo.infra.messaging.MessageChannels;
import ymyoo.infra.messaging.PollingMessageConsumer;

import java.util.concurrent.TimeUnit;

/**
 * Created by yooyoung-mo on 2017. 6. 22..
 */
public class OrderIntegrationTest {

    @Test
    public void placeOrder() throws Exception {
        Thread inventoryReplyMessageConsumer = new Thread(new PollingMessageConsumer(MessageChannels.INVENTORY_REPLY));
        inventoryReplyMessageConsumer.start();

        Thread paymentReplyMessageConsumer = new Thread(new PollingMessageConsumer(MessageChannels.PAYMENT_AUTH_APP_REPLY));
        paymentReplyMessageConsumer.start();

        // Given
        Order order = OrderFactory.create(new Orderer("유영모", "010-1111-2222", "gigamadness@gmail.com"),
                new OrderItem("P0003", 1, OrderItemDeliveryType.DIRECTING),
                new OrderPayment(2000, "123-456-0789"));

        // When
        String orderId = order.placeOrder();

        // Then
        // 주문 ID 반환 확인(Synchronized)
        Assert.assertTrue(StringUtils.isNotBlank(orderId));
        System.out.println(orderId);

        // 비동기 처리 대기
        waitCurrentThread(5);

        Assert.assertEquals(OrderStatus.PURCHASE_ORDER_CREATED, order.getStatus());
    }

    private void waitCurrentThread(int seconds) throws InterruptedException {
        Thread.currentThread().sleep(TimeUnit.SECONDS.toMillis(seconds));
    }

}
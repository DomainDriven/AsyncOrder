package ymyoo.app.order;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ymyoo.app.order.domain.command.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by yooyoung-mo on 2017. 7. 4..
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderApplicationTest {

    @Test
    public void placeOrder() throws Exception {
        // Given
        Order order = OrderFactory.create(new Orderer("유영모", "010-1111-2222", "gigamadness@gmail.com"),
                new OrderItem("P0003", 1, OrderItemDeliveryType.DIRECTING),
                new OrderPayment(2000, "123-456-0789"));

        // When
        String orderId = order.placeOrder();

        // Then
        // 주문 ID 반환 확인(Synchronized)
        Assert.assertTrue(StringUtils.isNotBlank(orderId));

        // 비동기 처리 대기
        waitCurrentThread(5);

        Assert.assertEquals(OrderStatus.PURCHASE_ORDER_CREATED, order.getStatus());
    }

    private void waitCurrentThread(int seconds) throws InterruptedException {
        Thread.currentThread().sleep(TimeUnit.SECONDS.toMillis(seconds));
    }

}
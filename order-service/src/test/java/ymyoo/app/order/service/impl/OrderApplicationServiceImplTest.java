package ymyoo.app.order.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ymyoo.app.order.domain.query.dto.PlacingOrderStatus;
import ymyoo.app.order.service.OrderApplicationService;
import ymyoo.app.order.service.WorkOrder;

import java.util.concurrent.TimeUnit;

/**
 * Created by yooyoung-mo on 2017. 7. 4..
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderApplicationServiceImplTest {

    @Autowired
    OrderApplicationService orderApplicationService;

    @Test
    public void placeOrder() throws Exception {
        // Given
        WorkOrder workOrder = new WorkOrder("유영모", "010-1111-2222",
                "gigamadness@gmail.com", "P0003", 1, 2000, "123-456");

        // When
        String orderId = orderApplicationService.placeOrder(workOrder);

        // Then
        // 주문 ID 반환 확인(Synchronized)
        Assert.assertTrue(StringUtils.isNotBlank(orderId));

        // 비동기 처리 대기
        waitCurrentThread(5);

        PlacingOrderStatus actualOrderStatus = orderApplicationService.getOrderStatus(orderId);
        Assert.assertEquals(PlacingOrderStatus.PURCHASE_ORDER_CREATED, actualOrderStatus);
    }

    private void waitCurrentThread(int seconds) throws InterruptedException {
        Thread.currentThread().sleep(TimeUnit.SECONDS.toMillis(seconds));
    }
}
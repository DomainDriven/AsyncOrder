package ymyoo.app.order.domain.command.workflow.activity;

import org.junit.Assert;
import org.junit.Test;
import ymyoo.app.order.domain.command.OrderStatus;

/**
 * Created by yooyoung-mo on 2017. 7. 6..
 */
public class AbstractBusinessActivityTest {

    @Test
    public void testPerformActivity() {
        // given
        String orderId = "1111";
        OrderStatus.Status beforeStatue = OrderStatus.Status.INVENTORY_REQUEST;
        OrderStatus.Status afterStatue = OrderStatus.Status.INVENTORY_CHECKED;

        BusinessActivity<Integer, Boolean> activity = new TestAbstractBusinessActivity(orderId, beforeStatue, afterStatue);

        // when
        Boolean actual = activity.perform(1);

        // then
        Assert.assertTrue(actual);
    }
}
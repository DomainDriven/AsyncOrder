package ymyoo.app.order.domain.command.workflow.activity;

import ymyoo.app.order.domain.command.OrderStatus;
import ymyoo.app.order.domain.command.workflow.activity.impl.AbstractBusinessActivity;

/**
 * Created by yooyoung-mo on 2017. 7. 6..
 */
public class TestAbstractBusinessActivity extends AbstractBusinessActivity<Integer, Boolean> {

    public TestAbstractBusinessActivity(String orderId, OrderStatus.Status beforePerformActivityStatus, OrderStatus.Status afterPerformActivityStatus) {
        super(orderId, beforePerformActivityStatus, afterPerformActivityStatus);
    }

    @Override
    public Boolean performActivity(Integer integer) {
        System.out.println("performActivity...");
        return Boolean.TRUE;

    }
}

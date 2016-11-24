package ymyoo.order.domain.workflow.activity.impl;

import ymyoo.infra.messaging.remote.queue.ReplyMessage;
import ymyoo.order.adapter.InventoryChannelAdapter;
import ymyoo.order.domain.Order;
import ymyoo.order.domain.workflow.activity.AsyncActivity;

/**
 * 주문시 수행 해야할 재고 관련 작업 모음
 *
 * Created by 유영모 on 2016-10-10.
 */
public class InventoryAsyncActivity implements AsyncActivity<ReplyMessage, Void> {
    private Order order;

    public InventoryAsyncActivity(Order order) {
        this.order = order;

    }

    @Override
    public void  perform() {
        // 외부 인터렉션은 어뎁터를 사용
        InventoryChannelAdapter adapter = new InventoryChannelAdapter();
        adapter.sendCheckingAndReservingOrderItem(order.getOrderId(), order.getOrderItem());
    }


    @Override
    public Void callback(ReplyMessage replyMessage) {
        if(replyMessage.getStatus() == ReplyMessage.ReplyMessageStatus.FAILURE) {
            throw new RuntimeException("재고 오류");
        }
        return null;
    }
}

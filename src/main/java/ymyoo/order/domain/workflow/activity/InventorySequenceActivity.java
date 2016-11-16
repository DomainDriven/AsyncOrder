package ymyoo.order.domain.workflow.activity;

import ymyoo.order.domain.Order;
import ymyoo.order.infra.messaging.queue.Message;
import ymyoo.order.infra.messaging.queue.MessageType;
import ymyoo.order.infra.messaging.queue.Requester;

/**
 * 주문시 수행 해야할 재고 관련 작업 모음
 *
 * Created by 유영모 on 2016-10-10.
 */
public class InventorySequenceActivity implements  SequenceActivity<Void> {
    private Order order;

    public InventorySequenceActivity(Order order) {
        this.order = order;

    }

    @Override
    public Void perform() {
        Message sendMessage = new Message();
        sendMessage.setId(order.getOrderId());
        sendMessage.setType(MessageType.CHECK_INVENTOY);
        sendMessage.setObjectProperty(order.getOrderItem());

        Requester requester = new Requester();
        requester.send(sendMessage);

        Message receivedMessage = requester.receive(order.getOrderId());

        if(receivedMessage.getId() == order.getOrderId()) {
            return null;
        } else {
            throw new RuntimeException("재고 오류");
        }
    }
}

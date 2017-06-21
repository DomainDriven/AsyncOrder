package ymyoo.app.inventory.adapter.messaging;

import ymyoo.infra.messaging.core.AbstractReplier;
import ymyoo.infra.messaging.core.MessageChannels;
import ymyoo.infra.messaging.core.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 유영모 on 2017-01-02.
 */
public class InventoryReplier extends AbstractReplier {
    public InventoryReplier() {
        super(MessageChannels.INVENTORY_REQUEST);
    }

    @Override
    public void onMessage(Message receivedMessage) {
        InventoryChannelAdapter channelAdapter = new InventoryChannelAdapter();
        channelAdapter.checkAndReserve(receivedMessage);

        // 결과를 메시지로 전송
        Map<String, String> replyMessageBody = new HashMap<>();
        replyMessageBody.put("orderId", getOrderId(receivedMessage));
        replyMessageBody.put("validation", "SUCCESS");

        final String channel = receivedMessage.getHeaders().get("returnAddress");
        final String correlationId = receivedMessage.getMessageId();
        sendMessage(channel, correlationId, replyMessageBody);
    }
}

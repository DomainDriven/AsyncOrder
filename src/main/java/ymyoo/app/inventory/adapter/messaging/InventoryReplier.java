package ymyoo.app.inventory.adapter.messaging;

import com.google.gson.Gson;
import ymyoo.messaging.AbstractReplier;
import ymyoo.messaging.Message;
import ymyoo.messaging.MessageChannels;
import ymyoo.messaging.MessageProducer;

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
    public void onMessage(String replyChannel, Message message) {
        InventoryChannelAdapter channelAdapter = new InventoryChannelAdapter();
        channelAdapter.checkAndReserve(message);

        // 결과를 메시지로 전송
        Map<String, String> replyMessageBody = new HashMap<>();
        replyMessageBody.put("validation", "SUCCESS");

        MessageProducer producer = new MessageProducer(replyChannel);
        producer.send(message.getId(), new Gson().toJson(replyMessageBody));
    }
}

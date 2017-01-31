package ymyoo.messaging.adapter;

import com.google.gson.Gson;
import ymyoo.messaging.core.MessageChannels;
import ymyoo.messaging.core.Requester;
import ymyoo.messaging.processor.entitiy.IncompleteBusinessActivity;
import ymyoo.messaging.processor.entitiy.OrderStatusEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 유영모 on 2017-01-13.
 */
public class MessageStoreChannelAdapter {

    public void storeOrderStatus(OrderStatusEntity aOrderStatus) {
        Requester requester = new Requester(MessageChannels.MESSAGE_STORE);

        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("type", MessageChannels.MESSAGE_STORE_TYPE_ORDER_STATUS);
        messageBody.put("message", aOrderStatus);

        requester.send(new Gson().toJson(messageBody));
    }

    public void storeIncompleteActivity(IncompleteBusinessActivity activity) {
        Requester requester = new Requester(MessageChannels.MESSAGE_STORE);

        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("type", MessageChannels.MESSAGE_STORE_TYPE_INCOMPLETE_BUSINESS_ACTIVITY);
        messageBody.put("message", activity);

        requester.send(new Gson().toJson(messageBody));
    }
}

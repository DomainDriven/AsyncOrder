package ymyoo.messaging.adapter;

import com.google.gson.Gson;
import ymyoo.app.order.domain.OrderStatus;
import ymyoo.messaging.core.MessageChannels;
import ymyoo.messaging.core.Requester;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 유영모 on 2017-01-13.
 */
public class MessageStoreChannelAdapter {

    public void storeOrderStatus(OrderStatus aOrderStatus) {
        String messageId =  java.util.UUID.randomUUID().toString().toUpperCase();
        Requester requester = new Requester(MessageChannels.MESSAGE_STORE, messageId);

        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("type", "ORDER-STATUS");
        messageBody.put("message", aOrderStatus);

        requester.send(new Gson().toJson(messageBody));
    }
}

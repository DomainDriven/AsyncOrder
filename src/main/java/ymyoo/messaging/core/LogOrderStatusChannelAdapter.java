package ymyoo.messaging.core;

import com.google.gson.Gson;
import ymyoo.app.order.domain.OrderStatus;
import ymyoo.messaging.core.MessageChannels;
import ymyoo.messaging.core.Requester;

/**
 * Created by 유영모 on 2017-01-13.
 */
public class LogOrderStatusChannelAdapter {

    public void logStatus(OrderStatus aOrderStatus) {
        String messageId =  java.util.UUID.randomUUID().toString().toUpperCase();
        Requester requester = new Requester(MessageChannels.LOG_ORDER_STATUS, messageId);

        requester.send(new Gson().toJson(aOrderStatus));
    }
}

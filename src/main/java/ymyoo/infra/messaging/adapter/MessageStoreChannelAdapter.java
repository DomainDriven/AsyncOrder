package ymyoo.infra.messaging.adapter;

import ymyoo.infra.messaging.core.MessageChannels;
import ymyoo.infra.messaging.core.Requester;
import ymyoo.infra.messaging.processor.entitiy.IncompleteBusinessActivity;
import ymyoo.infra.messaging.processor.entitiy.OrderStatusEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 유영모 on 2017-01-13.
 */
public class MessageStoreChannelAdapter {

    public void storeOrderStatus(OrderStatusEntity aOrderStatus) {
        Requester requester = new Requester(MessageChannels.MESSAGE_STORE);

        Map<String, String> headers = new HashMap<>();
        headers.put("type", MessageChannels.MESSAGE_STORE_TYPE_ORDER_STATUS);

        requester.send(headers, aOrderStatus);
    }

    public void storeIncompleteActivity(IncompleteBusinessActivity activity) {
        Requester requester = new Requester(MessageChannels.MESSAGE_STORE);

        Map<String, String> headers = new HashMap<>();
        headers.put("type", MessageChannels.MESSAGE_STORE_TYPE_INCOMPLETE_BUSINESS_ACTIVITY);

        System.out.println("storeIncompleteActivity:" + activity);
        requester.send(headers, activity);
    }
}

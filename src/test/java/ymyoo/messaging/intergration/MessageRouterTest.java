package ymyoo.messaging.intergration;

import com.google.gson.Gson;
import org.junit.Test;
import ymyoo.messaging.MessageChannels;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 유영모 on 2016-12-28.
 */
public class MessageRouterTest {
    @Test
    public void route() throws Exception {
        // given
        final String channel = MessageChannels.INVENTORY_REQUEST;
        final String key = java.util.UUID.randomUUID().toString().toUpperCase() + "::" + MessageChannels.INVENTORY_REPLY;
        Map<String, String> data = new HashMap<>();
        data.put("deliveryType", "DIRECTING");
        data.put("productId", "prd-11");
        data.put("orderQty", "2");

        // when
        MessageRouter router = new MessageRouter();
        router.route(channel, key, new Gson().toJson(data));

        // then
        // Replier 호출 여부 확인 ...
    }

}
package ymyoo.infra.messaging.remote.channel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;
import ymyoo.infra.messaging.remote.channel.message.Message;
import ymyoo.infra.messaging.remote.channel.message.MessageHead;
import ymyoo.order.domain.OrderIdGenerator;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 유영모 on 2016-11-16.
 */
public class ReplierTest {

    @Test
    public void testReply() {
        // given
        Thread r = new Thread(new Replier());
        r.start();

        String orderId = OrderIdGenerator.generate();
        MessageHead messageHead = new MessageHead(orderId, MessageHead.MessageType.CHECK_INVENTOY);
        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("deliveryType", "aaaa");
        messageBody.put("proudctId", "11111");
        messageBody.put("orderQty", "1");

        // when
        Requester requester = new Requester();
        requester.send(new Message(messageHead, new Gson().toJson(messageBody)));

        // then
        Message receivedMessage = requester.receive(orderId);

        Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        Map<String, String> content = new Gson().fromJson(receivedMessage.getBody(), type);
        Assert.assertEquals("SUCCESS", content.get("validation"));
    }
}

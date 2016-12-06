package ymyoo.infra.messaging.remote.channel;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by 유영모 on 2016-12-06.
 */
public class MessageConsumerTest {

    @Test
    public void testReceive() {
        // given
        String requestChannel = "spike-request";
        String replyChannel = "spike-reply";
        String messageId = "111111";
        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("test", "test");

        // when
        MessageConsumer consumer = new MessageConsumer(replyChannel);
        String actual = consumer.receive(messageId);

        // then
        Assert.assertEquals(new Gson().toJson(messageBody), actual);

    }

}
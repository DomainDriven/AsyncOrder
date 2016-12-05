package ymyoo.infra.messaging.remote.channel;

import com.google.gson.Gson;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by 유영모 on 2016-12-05.
 */
public class MessageProducerTest {

    @Test
    public void testSend() {
        // given
        String requestChannel = "spike-request";
        String replyChannel = "spike-reply";
        String messageId = "111111";
        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("test", "test");

        Producer<String, String> mockProducer = mock(Producer.class);
        ArgumentCaptor<ProducerRecord> argument = ArgumentCaptor.forClass(ProducerRecord.class);


        // when
        TestMessageProducer producer = new TestMessageProducer(requestChannel, replyChannel, mockProducer);
        producer.send(messageId, new Gson().toJson(messageBody));

        // then
        verify(mockProducer).send(argument.capture());
        Assert.assertEquals("spike-request", argument.getValue().topic());
        Assert.assertEquals("111111::spike-reply", argument.getValue().key());
        Assert.assertEquals(new Gson().toJson(messageBody), argument.getValue().value());
    }
}

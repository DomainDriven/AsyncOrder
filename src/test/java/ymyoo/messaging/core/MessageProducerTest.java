package ymyoo.messaging.core;

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
        String requestChannel = "TEST-REQUEST";
        String replyChannel = "TEST-REPLY";
        String messageId = "111111";
        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("test", "test");

        Producer<String, String> mockProducer = mock(Producer.class);
        ArgumentCaptor<ProducerRecord> argument = ArgumentCaptor.forClass(ProducerRecord.class);


        // when
        MessageProducer producer = new MessageProducer(requestChannel, replyChannel, mockProducer);
        producer.send(messageId, new Gson().toJson(messageBody));

        // then
        verify(mockProducer).send(argument.capture());
        Assert.assertEquals("TEST-REQUEST", argument.getValue().topic());
        Assert.assertEquals("111111::TEST-REPLY", argument.getValue().key());
        Assert.assertEquals(new Gson().toJson(messageBody), argument.getValue().value());
    }
}

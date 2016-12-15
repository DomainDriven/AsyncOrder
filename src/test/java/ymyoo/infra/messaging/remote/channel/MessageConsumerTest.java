package ymyoo.infra.messaging.remote.channel;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * Created by 유영모 on 2016-12-15.
 */
public class MessageConsumerTest {

    @Before
    public void setUp() throws Exception {
        TestMessageConsumer.clearCallbackList();
    }

    @Test
    public void registerCallback() throws Exception {
        // given
        KafkaConsumer<String, String> mockKafkaConsumer = mock(KafkaConsumer.class);
        TestMessageConsumer consumer = new TestMessageConsumer("testChannel", mockKafkaConsumer);

        // when
        consumer.registerCallback(new Callback() {
            @Override
            public void call(Object result) {

            }

            @Override
            public Object translate(String data) {
                return null;
            }

            @Override
            public String getId() {
                return null;
            }
        });

        // then
        Assert.assertEquals(1, consumer.getSizeForCallback());
    }

    @Test
    public void unregisterCallback() throws Exception {
        // given
        KafkaConsumer<String, String> mockKafkaConsumer = mock(KafkaConsumer.class);
        TestMessageConsumer consumer = new TestMessageConsumer("testChannel", mockKafkaConsumer);
        Callback callback = new Callback() {
            @Override
            public void call(Object result) {

            }

            @Override
            public Object translate(String data) {
                return null;
            }

            @Override
            public String getId() {
                return null;
            }
        };
        consumer.registerCallback(callback);

        // when
        consumer.unregisterCallback(callback);

        // then
        Assert.assertEquals(0, consumer.getSizeForCallback());
    }

    @Test
    public void run() throws Exception {
        // given
        KafkaConsumer<String, String> mockKafkaConsumer = mock(KafkaConsumer.class);

        Map<TopicPartition, List<ConsumerRecord<String, String>>> records = new HashMap<>();
        records.put(new TopicPartition("test-topic", 1),
                Arrays.asList(new ConsumerRecord("test-topic", 1, 0, "12345", "test!")));

        when(mockKafkaConsumer.poll(100)).thenReturn(new ConsumerRecords(records));

        MessageConsumer.registerCallback(new Callback() {
            @Override
            public void call(Object result) {
                Assert.assertEquals("test!", result);
            }

            @Override
            public Object translate(String data) {
                return "test!";
            }

            @Override
            public String getId() {
                return "12345";
            }
        });

        // when
        Thread messageConsumer = new Thread(new MessageConsumer("test-channel", mockKafkaConsumer));
        messageConsumer.start();

        Thread.sleep(1000);

        messageConsumer.interrupt();

        // then
        verify(mockKafkaConsumer).subscribe(Arrays.asList("test-channel"));
    }
}
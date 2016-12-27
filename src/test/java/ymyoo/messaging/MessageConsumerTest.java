package ymyoo.messaging;

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
        TestMessageConsumer.clearListeners();
    }

    @Test
    public void registerListener() throws Exception {
        // given
        KafkaConsumer<String, String> mockKafkaConsumer = mock(KafkaConsumer.class);
        TestMessageConsumer consumer = new TestMessageConsumer("testChannel", mockKafkaConsumer);

        // when
        consumer.registerListener(new MessageListener() {
            @Override
            public void onMessage(String message) {

            }

            @Override
            public String getCorrelationId() {
                return null;
            }
        });

        // then
        Assert.assertEquals(1, consumer.getListenerSize());
    }

    @Test
    public void unregisterCallback() throws Exception {
        // given
        KafkaConsumer<String, String> mockKafkaConsumer = mock(KafkaConsumer.class);
        TestMessageConsumer consumer = new TestMessageConsumer("testChannel", mockKafkaConsumer);
        MessageListener listener = new MessageListener() {
            @Override
            public void onMessage(String message) {

            }

            @Override
            public String getCorrelationId() {
                return null;
            }
        };

        consumer.registerListener(listener);

        // when
        consumer.unregisterListener(listener);

        // then
        Assert.assertEquals(0, consumer.getListenerSize());
    }

    @Test
    public void run() throws Exception {
        // given
        KafkaConsumer<String, String> mockKafkaConsumer = mock(KafkaConsumer.class);

        Map<TopicPartition, List<ConsumerRecord<String, String>>> records = new HashMap<>();
        records.put(new TopicPartition("test-topic", 1),
                Arrays.asList(new ConsumerRecord("test-topic", 1, 0, "12345", "test!")));

        when(mockKafkaConsumer.poll(100)).thenReturn(new ConsumerRecords(records));

        MessageConsumer.registerListener(new MessageListener() {
            @Override
            public void onMessage(String message) {

            }

            @Override
            public String getCorrelationId() {
                return "12345";
            }
        });

        // when
        Thread messageConsumer = new Thread(new MessageConsumer("test-intergration", mockKafkaConsumer));
        messageConsumer.start();

        Thread.sleep(1000);

        messageConsumer.interrupt();

        // then
        verify(mockKafkaConsumer).subscribe(Arrays.asList("test-intergration"));
    }
}
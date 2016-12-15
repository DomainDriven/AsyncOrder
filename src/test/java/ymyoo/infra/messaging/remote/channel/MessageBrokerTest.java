package ymyoo.infra.messaging.remote.channel;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * Created by 유영모 on 2016-12-08.
 */
@RunWith(PowerMockRunner.class)
public class MessageBrokerTest {

    @Test
    @PrepareForTest(MessageRouter.class)
    public void testRun() throws Exception {
        // given

        KafkaConsumer<String, String> mockKafkaConsumer = mock(KafkaConsumer.class);

        Map<TopicPartition, List<ConsumerRecord<String, String>>> records = new HashMap<>();
        records.put(new TopicPartition("test-channel1", 1),
                Arrays.asList(new ConsumerRecord("test-channel1", 1, 0, "12345", "test!")));

        when(mockKafkaConsumer.poll(100)).thenReturn(new ConsumerRecords(records));

        PowerMockito.mockStatic(MessageRouter.class);

        // when
        Thread messageConsumer = new Thread(new MessageBroker(mockKafkaConsumer, "test-channel1", "test-channel2"));
        messageConsumer.start();
        Thread.sleep(1000);
        messageConsumer.interrupt();

        // then
        verify(mockKafkaConsumer).subscribe(Arrays.asList("test-channel1", "test-channel2"));

        PowerMockito.verifyStatic(Mockito.atLeast(1));
        MessageRouter.route("test-channel1", "12345", "test!");
    }

}
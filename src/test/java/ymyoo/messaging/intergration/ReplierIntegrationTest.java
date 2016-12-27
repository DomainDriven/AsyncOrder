package ymyoo.messaging.intergration;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by 유영모 on 2016-12-27.
 */
public class ReplierIntegrationTest {
    static boolean messageReceivedFlag = false;
    final String TEST_REPLY_CHANNEL = "TEST-REPLY";


    @Before
    public void setUp() throws Exception {
        messageReceivedFlag = false;
    }

    @Test
    public void reply() throws InterruptedException {
        // given
        String replyChannel = TEST_REPLY_CHANNEL;
        String correlationId =  java.util.UUID.randomUUID().toString().toUpperCase();
        String replyMessage = "test reply message";

        // when
        Replier replier = new Replier();
        replier.reply(replyChannel, correlationId, replyMessage);

        // then
        new Thread(() -> {
            Properties props = new Properties();
            props.put("bootstrap.servers", "localhost:9092");
            props.put("group.id", "test");
            props.put("enable.auto.commit", "true");
            props.put("auto.commit.interval.ms", "1000");
            props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

            consumer.subscribe(Arrays.asList(TEST_REPLY_CHANNEL));
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    ConsumerRecords<String, String> records = consumer.poll(100);
                    for (ConsumerRecord<String, String> record : records) {
                        if(record.key().equals(correlationId)) {
                            Assert.assertEquals(replyMessage, record.value());
                            ReplierIntegrationTest.messageReceivedFlag = true;
                        }
                    }
                }
            } finally {
                consumer.close();
            }
        }).start();

        // 비동기 대기
        Object obj = new Object();
        synchronized (obj) {
            obj.wait(TimeUnit.SECONDS.toMillis(7));
        }

        Assert.assertTrue(ReplierIntegrationTest.messageReceivedFlag);
    }

}
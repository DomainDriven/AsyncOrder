package ymyoo.messaging;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;


/**
 * Created by 유영모 on 2016-12-06.
 */
public class MessageConsumerIntegrationTest {

    @Test
    public void testRun() throws InterruptedException {
        // given
        String channelName = "TEST-REPLY";
        Thread r = new Thread(new MessageConsumer(channelName));
        r.start();

        String callback1Key = "12345";
        String callback1Value = "hello";
//        MessageConsumer.registerCallback(new Callback() {
//            @Override
//            public void call(Object result) {
//                // then
//                Assert.assertEquals(result, callback1Value);
//            }
//
//            @Override
//            public Object translate(String data) {
//                // then
//                return callback1Value;
//            }
//
//            @Override
//            public String getId() {
//                return callback1Key;
//            }
//        });

        MessageConsumer.registerListener(new MessageListener() {
            @Override
            public void onMessage(String message) {
                // then
                Assert.assertEquals(message, callback1Value);
            }

            @Override
            public String getCorrelationId() {
                return callback1Key;
            }
        });

        String callback2Key = "67890";
        String callback2Value = "bye!";
//        MessageConsumer.registerCallback(new Callback() {
//            @Override
//            public void call(Object result) {
//                // then
//                Assert.assertEquals(result, callback2Value);
//            }
//
//            @Override
//            public Object translate(String data) {
//                // then
//                Assert.assertEquals(data, callback2Value);
//                return callback2Value;
//            }
//
//            @Override
//            public String getId() {
//                return callback2Key;
//            }
//        });

        MessageConsumer.registerListener(new MessageListener() {
            @Override
            public void onMessage(String message) {
                Assert.assertEquals(message, callback2Value);
            }

            @Override
            public String getCorrelationId() {
                return callback2Key;
            }
        });

        // when
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        producer.send(new ProducerRecord<>("TEST-REPLY", callback1Key, callback1Value));
        producer.send(new ProducerRecord<>("TEST-REPLY", callback2Key, callback2Value));

        producer.close();

        // 비동기 처리 대기
        synchronized (r) {
            r.wait(5000);
        }
    }

}
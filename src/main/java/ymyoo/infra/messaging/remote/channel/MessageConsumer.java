package ymyoo.infra.messaging.remote.channel;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.*;

/**
 * Created by 유영모 on 2016-12-06.
 */
public class MessageConsumer implements Runnable {
    private String channel;
    private KafkaConsumer<String, String> consumer;
    protected static List<Callback> callbackList = Collections.synchronizedList(new ArrayList());

    public MessageConsumer(String channel) {
        this.channel = channel;

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        this.consumer = new KafkaConsumer<>(props);
    }

    public MessageConsumer(String channel, KafkaConsumer<String, String> consumer) {
        this.channel = channel;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        consumer.subscribe(Arrays.asList(channel));

        try {
            while (!Thread.currentThread().isInterrupted()) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    for(Callback callback : callbackList) {
                        if( (callback.getId().equals(record.key())) ) {
                            Object translatedData = callback.translate(record.value());
                            callback.call(translatedData);
                            MessageConsumer.unregisterCallback(callback);
                            break;
                        }
                    }
                }
            }
        } finally {
            consumer.close();
        }

    }

    public static void registerCallback(Callback callback)  {
        synchronized (callbackList) {
            callbackList.add(callback);
        }

    }

    public static void unregisterCallback(Callback callback) {
        synchronized (callbackList) {
            callbackList.remove(callback);
        }
    }

}

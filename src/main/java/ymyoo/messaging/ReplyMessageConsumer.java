package ymyoo.messaging;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.*;

/**
 * Created by 유영모 on 2016-12-06.
 */
public class ReplyMessageConsumer implements Runnable {
    private String channel;
    private KafkaConsumer<String, String> consumer;
    protected static List<MessageListener> listeners = Collections.synchronizedList(new ArrayList());

    public ReplyMessageConsumer(String channel) {
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

    public ReplyMessageConsumer(String channel, KafkaConsumer<String, String> consumer) {
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
                    for(MessageListener listener : listeners) {
                        if( (listener.getCorrelationId().equals(record.key())) ) {
                            listener.onMessage(record.value());
                            ReplyMessageConsumer.unregisterListener(listener);
                            break;
                        }
                    }
                }
            }
        } finally {
            consumer.close();
        }
    }

    public static void registerListener(MessageListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }

    }

    public static void unregisterListener(MessageListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }
}

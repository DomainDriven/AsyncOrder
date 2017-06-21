package ymyoo.infra.messaging.core;

import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * Created by 유영모 on 2016-12-15.
 */
public class TestPollingMessageConsumer extends PollingMessageConsumer {
    public TestPollingMessageConsumer(String channel, KafkaConsumer<String, String> consumer) {
        super(channel, consumer);
    }

    public int getListenerSize() {
        return listeners.size();
    }

    public static void clearListeners() {
        listeners.clear();
    }
}

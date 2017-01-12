package ymyoo.messaging.core;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import ymyoo.messaging.core.ReplyMessageConsumer;

/**
 * Created by 유영모 on 2016-12-15.
 */
public class TestReplyMessageConsumer extends ReplyMessageConsumer {
    public TestReplyMessageConsumer(String channel, KafkaConsumer<String, String> consumer) {
        super(channel, consumer);
    }

    public int getListenerSize() {
        return listeners.size();
    }

    public static void clearListeners() {
        listeners.clear();
    }
}

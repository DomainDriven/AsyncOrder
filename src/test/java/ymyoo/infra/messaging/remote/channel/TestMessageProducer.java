package ymyoo.infra.messaging.remote.channel;

import org.apache.kafka.clients.producer.Producer;

/**
 * Created by 유영모 on 2016-12-05.
 */
public class TestMessageProducer extends MessageProducer {
    public TestMessageProducer(String requestChannel, String replyChannel, Producer<String, String> producer) {
        super(requestChannel, replyChannel, producer);
    }

    @Override
    public void send(String messageId, String messageBody) {
        super.send(messageId, messageBody);
    }
}

package ymyoo.infra.messaging.remote.channel;

import org.apache.kafka.clients.producer.Producer;

/**
 * 실제 메시지를 생산하는 Producer 를 Mocking 하는 테스트 클래스
 *
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

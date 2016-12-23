package ymyoo.order.messaging.endpoint.request;

import ymyoo.infra.messaging.remote.channel.MessageProducer;

/**
 * Created by 유영모 on 2016-12-20.
 */
public class Requester {
    public void send(String requestChannel, String replyChannel, String correlationId, String message) {
        MessageProducer producer = new MessageProducer(requestChannel, replyChannel);
        producer.send(correlationId, message);
    }

    public void send(String requestChannel, String messageId, String message) {
        MessageProducer producer = new MessageProducer(requestChannel, "");
        producer.send(messageId, message);
    }

}

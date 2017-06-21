package ymyoo.infra.messaging.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 유영모 on 2017-01-02.
 */
public abstract class AbstractReplier implements Runnable {
    private final String channel;

    public AbstractReplier(String channel) {
        this.channel = channel;
    }

    public abstract void onMessage(Message message);

    @Override
    public void run() {
        EventDrivenMessageConsumer eventDrivenMessageConsumer = new EventDrivenMessageConsumer(channel);
        try {
            while (!Thread.currentThread().isInterrupted()) {
                List<Message> messages = eventDrivenMessageConsumer.poll();
                for(Message message : messages) {
                    onMessage(message);
                }
            }
        } finally {
            eventDrivenMessageConsumer.close();
        }
    }

    protected String getOrderId(Message message) {
        return (String)((Map)message.getBody()).get("orderId");
    }

    private String generateMessageId() {
        return java.util.UUID.randomUUID().toString().toUpperCase();
    }

    protected void sendMessage(final String channel, final String correlationId, final Object body) {
        Map<String, String> headers = new HashMap<>();
        headers.put("correlationId", correlationId);

        final String messageId = generateMessageId();
        Message message = new Message(messageId, headers, body);
        MessageProducer producer = new MessageProducer();
        producer.send(channel, message);
    }
}

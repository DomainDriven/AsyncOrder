package ymyoo.messaging.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 유영모 on 2016-12-20.
 */
public class Requester implements MessageListener {
    final private String requestChannel;
    final private String replyChannel;
    final private String messageId = generateMessageId();
    private Message replyMessage;

    private String generateMessageId() {
        return java.util.UUID.randomUUID().toString().toUpperCase();
    }

    public Requester(String requestChannel) {
        this.requestChannel = requestChannel;
        this.replyChannel = "";
    }

    public Requester(String requestChannel, String replyChannel) {
        this.requestChannel = requestChannel;
        this.replyChannel = replyChannel;
    }

    public void send(Object messageBody) {
        Map<String, String> headers = new HashMap<>();
        headers.put("replyChannel", replyChannel);

        Message message = new Message(messageId, headers, messageBody);
        MessageProducer producer = new MessageProducer();
        producer.send(requestChannel, message);
    }

    public void send(Map<String, String> headers, Object messageBody) {
        Message message = new Message(messageId, headers, messageBody);
        MessageProducer producer = new MessageProducer();
        producer.send(requestChannel, message);
    }

    public synchronized Message receive() {
        PollingMessageConsumer.registerListener(this);

        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return replyMessage;
    }

    @Override
    public synchronized void onMessage(Message message) {
        replyMessage = message;
        this.notify();
    }

    @Override
    public String getCorrelationId() {
        return messageId;
    }
}
package ymyoo.messaging.core;

/**
 * Created by 유영모 on 2016-12-20.
 */
public class Requester implements MessageListener {
    final private String requestChannel;
    final private String replyChannel;
    final private String messageId = generateMessageId();
    private String replyMessage = "";

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

    public void send(String message) {
        MessageProducer producer = new MessageProducer(requestChannel, replyChannel);
        producer.send(messageId, message);
    }

    public synchronized String receive() {
        PollingMessageConsumer.registerListener(this);

        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return replyMessage;
    }

    @Override
    public synchronized void onMessage(String message) {
        replyMessage = message;
        this.notify();
    }

    @Override
    public String getCorrelationId() {
        return messageId;
    }
}
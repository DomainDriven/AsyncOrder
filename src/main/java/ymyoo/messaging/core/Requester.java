package ymyoo.messaging.core;

/**
 * Created by 유영모 on 2016-12-20.
 */
public class Requester implements MessageListener {
    private String requestChannel;
    private String replyChannel = "";
    private String correlationId;
    private String replyMessage = "";

    public Requester(String requestChannel, String correlationId) {
        this.requestChannel = requestChannel;
        this.correlationId = correlationId;
    }

    public Requester(String requestChannel, String replyChannel, String correlationId) {
        this.requestChannel = requestChannel;
        this.replyChannel = replyChannel;
        this.correlationId = correlationId;
    }

    public void send(String message) {
        MessageProducer producer = new MessageProducer(requestChannel, replyChannel);
        producer.send(correlationId, message);
    }

    public synchronized String receive() {
        ReplyMessageConsumer.registerListener(this);

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
        return correlationId;
    }
}
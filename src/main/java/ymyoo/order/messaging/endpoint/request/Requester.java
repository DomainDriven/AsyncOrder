package ymyoo.order.messaging.endpoint.request;

import ymyoo.infra.messaging.remote.channel.Callback;
import ymyoo.infra.messaging.remote.channel.MessageConsumer;
import ymyoo.infra.messaging.remote.channel.MessageProducer;

/**
 * Created by 유영모 on 2016-12-20.
 */
public class Requester implements Callback<String> {
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
        MessageConsumer.registerCallback(this);

        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return replyMessage;
    }

    @Override
    public synchronized void call(String result) {
        replyMessage = result;
        this.notify();
    }

    @Override
    public String translate(String data) {
        return data;
    }

    @Override
    public String getId() {
        return correlationId;
    }
}

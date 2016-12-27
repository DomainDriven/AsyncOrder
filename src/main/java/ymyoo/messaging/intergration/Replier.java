package ymyoo.messaging.intergration;

import ymyoo.messaging.MessageProducer;

/**
 * Created by 유영모 on 2016-12-20.
 */
public class Replier {
    public void reply(String channel, String id, String message) {
        MessageProducer producer = new MessageProducer(channel);
        producer.send(id, message);
    }
}

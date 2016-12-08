package ymyoo.infra.messaging.remote.channel;

import org.junit.Test;

/**
 * Created by 유영모 on 2016-12-08.
 */
public class MessageBrokerTest {
    @Test
    public void testRun() throws Exception {
        // given
        Thread r = new Thread(new MessageBroker("INVENTORY-REQUEST", "PAYMENT-AUTH-APP-REQUEST"));
        r.start();

        // when




        // then
    }

}
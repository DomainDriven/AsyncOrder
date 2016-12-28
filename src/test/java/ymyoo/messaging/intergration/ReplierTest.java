package ymyoo.messaging.intergration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ymyoo.messaging.MessageProducer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.whenNew;


/**
 * Created by 유영모 on 2016-12-27.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Replier.class)
public class ReplierTest {

    @Test
    public void reply() throws Exception {
        // given
        final String TEST_REPLY_CHANNEL = "TEST-REPLY";
        final String messageId = java.util.UUID.randomUUID().toString().toUpperCase();
        final String message = "Test";
        MessageProducer mockProducer = mock(MessageProducer.class);
        whenNew(MessageProducer.class).withArguments(TEST_REPLY_CHANNEL).thenReturn(mockProducer);

        // when
        Replier replier = new Replier();
        replier.reply(TEST_REPLY_CHANNEL, messageId, message);

        // then
        verifyNew(MessageProducer.class).withArguments(TEST_REPLY_CHANNEL);
        verify(mockProducer).send(messageId, message);
    }

}
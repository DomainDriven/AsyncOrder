package ymyoo.infra.messaging.core.interceptor;

import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.Map;

/**
 * Created by 유영모 on 2017-01-16.
 */
public class MessageConsumerInterceptorImpl implements ConsumerInterceptor {
    @Override
    public ConsumerRecords onConsume(ConsumerRecords consumerRecords) {
        return consumerRecords;
    }

    @Override
    public void onCommit(Map map) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}

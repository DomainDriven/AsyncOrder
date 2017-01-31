package ymyoo.app.notification.adapter.messaging;

import ymyoo.app.notification.domain.PurchaseNotification;
import ymyoo.messaging.adapter.MessageStoreChannelAdapter;
import ymyoo.messaging.core.EventDrivenMessageConsumer;
import ymyoo.messaging.core.Message;
import ymyoo.messaging.processor.entitiy.IncompleteBusinessActivity;

import java.util.List;

/**
 * Created by 유영모 on 2017-01-25.
 */
public class NotificationMessageConsumer implements Runnable {
    private String channel;

    public NotificationMessageConsumer(String channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        EventDrivenMessageConsumer eventDrivenMessageConsumer = new EventDrivenMessageConsumer(channel);
        try {
            while (!Thread.currentThread().isInterrupted()) {
                List<Message> messages = eventDrivenMessageConsumer.poll();
                for(Message message : messages) {
                    NotificationChannelAdapter adapter = new NotificationChannelAdapter();
                    try {
                        adapter.notifyToPurchaser(message.getBody());
                    } catch (Exception e) {
                        // 오류 시 MessageStore 채널로 내용 전송
                        PurchaseNotification purchaseNotification =
                                NotificationChannelAdapter.NotificationMessageTranslator.translate(message.getBody());
                        String activity = e.getMessage();

                        IncompleteBusinessActivity incompleteBusinessActivity =
                                new IncompleteBusinessActivity(purchaseNotification.getOrderId(), activity);

                        MessageStoreChannelAdapter messageStoreChannelAdapter = new MessageStoreChannelAdapter();
                        messageStoreChannelAdapter.storeIncompleteActivity(incompleteBusinessActivity);
                    }
                }
            }
        } finally {
            eventDrivenMessageConsumer.close();
        }

    }
}

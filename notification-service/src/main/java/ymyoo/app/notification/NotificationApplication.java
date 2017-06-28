package ymyoo.app.notification;

import ymyoo.app.notification.adapter.messaging.NotificationMessageConsumer;
import ymyoo.infra.messaging.MessageChannels;

/**
 * Created by yooyoung-mo on 2017. 6. 23..
 */
public class NotificationApplication {
  public static void main(String[] args) {
    Thread notificationMessageConsumer = new Thread(new NotificationMessageConsumer(MessageChannels.PURCHASE_ORDER_CREATED));
    notificationMessageConsumer.start();

  }
}

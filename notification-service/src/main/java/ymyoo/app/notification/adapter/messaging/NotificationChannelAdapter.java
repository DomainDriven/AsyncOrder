package ymyoo.app.notification.adapter.messaging;

import ymyoo.app.notification.domain.NotificationService;
import ymyoo.app.notification.domain.PurchaseNotification;
import ymyoo.infra.messaging.Message;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by 유영모 on 2017-01-25.
 */
public class NotificationChannelAdapter {
    private NotificationService notificationService = new NotificationService();

    public void notifyToPurchaser(Message message) throws Exception {
        PurchaseNotification purchaseNotification = NotificationMessageTranslator.translate((Map)message.getBody());

        notificationService.sendEmail(purchaseNotification);
        notificationService.sendMMS(purchaseNotification);
    }

    static class NotificationMessageTranslator {
        public static PurchaseNotification translate(Map data) throws InvocationTargetException, IllegalAccessException {
            PurchaseNotification purchaseNotification = new PurchaseNotification();
            purchaseNotification.setOrderId((String)data.get("orderId"));

            Map purchaser = (Map)data.get("purchaser");
            purchaseNotification.setCellPhone((String)purchaser.get("contactNumber"));
            purchaseNotification.setEmail((String)purchaser.get("email"));

            return purchaseNotification;
        }
    }
}

package ymyoo.app.notification.adapter.messaging;

import com.google.gson.Gson;
import ymyoo.app.notification.domain.NotificationService;
import ymyoo.app.notification.domain.PurchaseNotification;

/**
 * Created by 유영모 on 2017-01-25.
 */
public class NotificationChannelAdapter {
    private NotificationService notificationService = new NotificationService();

    public void notifyToPurchaser(PurchaseNotification purchaseNotification) throws Exception {
        notificationService.sendEmail(purchaseNotification.getEmail());
        notificationService.sendMMS(purchaseNotification.getCellPhone());
    }

    static class NotificationMessageTranslator {
        public static PurchaseNotification translate(String data) {
            return new Gson().fromJson(data, PurchaseNotification.class);
        }
    }
}

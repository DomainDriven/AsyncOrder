package ymyoo.app.notification.adapter.messaging;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ymyoo.app.notification.domain.NotificationService;
import ymyoo.app.notification.domain.PurchaseNotification;

/**
 * Created by 유영모 on 2017-01-25.
 */
public class NotificationChannelAdapter {
    private NotificationService notificationService = new NotificationService();

    public void notifyToPurchaser(String message) throws Exception {
        PurchaseNotification purchaseNotification = NotificationMessageTranslator.translate(message);
        notificationService.sendEmail(purchaseNotification);
        notificationService.sendMMS(purchaseNotification);
    }

    static class NotificationMessageTranslator {
        public static PurchaseNotification translate(String data) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(PurchaseNotification.class, new PurchaseNotificationDeserializer());
            Gson gson = gsonBuilder.create();

            return gson.fromJson(data, PurchaseNotification.class);
        }
    }
}

package ymyoo.app.notification.adapter.messaging;

import com.google.gson.*;
import ymyoo.app.notification.domain.PurchaseNotification;

import java.lang.reflect.Type;

/**
 * Created by 유영모 on 2017-01-26.
 */
public class PurchaseNotificationDeserializer implements JsonDeserializer<PurchaseNotification> {

    @Override
    public PurchaseNotification deserialize(JsonElement jsonElement, Type type
            , JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String orderId = jsonObject.get("orderId").getAsString();

        JsonObject purchaser = jsonObject.getAsJsonObject("purchaser");
        String email = purchaser.get("email").getAsString();
        String cellPhone = purchaser.get("contactNumber").getAsString();

        return new PurchaseNotification(orderId, email, cellPhone);
    }
}

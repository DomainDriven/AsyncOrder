package ymyoo.messaging.processor.deserializer;

import com.google.gson.*;
import ymyoo.messaging.processor.entitiy.IncompleteBusinessActivity;

import java.lang.reflect.Type;

/**
 * Created by 유영모 on 2017-01-25.
 */
public class IncompleteBusinessActivityDeserializer implements JsonDeserializer<IncompleteBusinessActivity> {

    @Override
    public IncompleteBusinessActivity deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonObject message = jsonObject.getAsJsonObject("message");

        String orderId = message.get("orderId").getAsString();
        String activity = message.get("activity").getAsString();

        return new IncompleteBusinessActivity(orderId, activity);
    }
}

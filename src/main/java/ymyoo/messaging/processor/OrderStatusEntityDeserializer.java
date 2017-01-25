package ymyoo.messaging.processor;

import com.google.gson.*;
import ymyoo.messaging.processor.entitiy.OrderStatusEntity;

import java.lang.reflect.Type;

/**
 * Created by 유영모 on 2017-01-25.
 */
public class OrderStatusEntityDeserializer implements JsonDeserializer<OrderStatusEntity> {
    @Override
    public OrderStatusEntity deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonObject message = jsonObject.getAsJsonObject("message");

        String orderId = message.get("orderId").getAsString();
        String status = message.get("status").getAsString();

        final OrderStatusEntity orderStatus = new OrderStatusEntity();
        orderStatus.setOrderId(orderId);
        orderStatus.setStatus(OrderStatusEntity.Status.valueOf(status));
        return orderStatus;
    }
}

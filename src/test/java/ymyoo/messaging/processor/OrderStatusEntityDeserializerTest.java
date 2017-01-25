package ymyoo.messaging.processor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.Test;
import ymyoo.messaging.processor.entitiy.OrderStatusEntity;

/**
 * Created by 유영모 on 2017-01-25.
 */
public class OrderStatusEntityDeserializerTest {

    @Test
    public void deserialize() throws Exception {
        // given
        String jsonString = "{" +
                "  \"type\" : \"ORDER-STATUS\"," +
                "  \"message\" : {" +
                "   \"orderId\":  \"11111\"," +
                "   \"status\":  \"INVENTORY_CHECKED\"" +
                "  }" +
                "}";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(OrderStatusEntity.class, new OrderStatusEntityDeserializer());

        // when
        Gson gson = gsonBuilder.create();
        OrderStatusEntity actual = gson.fromJson(jsonString, OrderStatusEntity.class);

        // then
        Assert.assertEquals("11111", actual.getOrderId());
        Assert.assertEquals(OrderStatusEntity.Status.INVENTORY_CHECKED, actual.getStatus());
    }

}
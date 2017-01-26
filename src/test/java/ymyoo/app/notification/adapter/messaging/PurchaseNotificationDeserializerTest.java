package ymyoo.app.notification.adapter.messaging;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.Test;
import ymyoo.app.notification.domain.PurchaseNotification;

/**
 * Created by 유영모 on 2017-01-26.
 */
public class PurchaseNotificationDeserializerTest {

    @Test
    public void deserialize() throws Exception {
        // given
        String jsonString = "{" +
                "  \"orderId\": \"201710101012222\"," +
                "  \"orderItem\": {" +
                "    \"deliveryType\": \"DIRECTING\"," +
                "    \"orderQty\": 1," +
                "    \"productId\": \"prd-123\"" +
                "  }," +
                "  \"orderPayment\": {" +
                "    \"tid\": \"tid-123445\"" +
                "  }," +
                "  \"purchaser\": {" +
                "    \"contactNumber\": \"010-1111-2222\"," +
                "    \"email\": \"gigamadness@gmail.com\"," +
                "    \"name\": \"유영모\"" +
                "  }" +
                "}";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(PurchaseNotification.class, new PurchaseNotificationDeserializer());

        // when
        Gson gson = gsonBuilder.create();
        PurchaseNotification actual = gson.fromJson(jsonString, PurchaseNotification.class);

        // then
        Assert.assertEquals("201710101012222", actual.getOrderId());
        Assert.assertEquals("010-1111-2222", actual.getCellPhone());
        Assert.assertEquals("gigamadness@gmail.com", actual.getEmail());
    }

}
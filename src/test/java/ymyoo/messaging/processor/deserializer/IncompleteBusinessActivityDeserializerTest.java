package ymyoo.messaging.processor.deserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.Test;
import ymyoo.messaging.processor.entitiy.IncompleteBusinessActivity;

/**
 * Created by 유영모 on 2017-01-25.
 */
public class IncompleteBusinessActivityDeserializerTest {
    @Test
    public void deserialize() throws Exception {
        // given
        String jsonString = "{" +
                "  \"type\" : \"INCOMPLETE-BUSINESS-ACTIVITY\"," +
                "  \"message\" : {" +
                "   \"orderId\":  \"11111\"," +
                "   \"activity\":  \"SENDING_CELL_PHONE\"" +
                "  }" +
                "}";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(IncompleteBusinessActivity.class, new IncompleteBusinessActivityDeserializer());

        // when
        Gson gson = gsonBuilder.create();
        IncompleteBusinessActivity actual = gson.fromJson(jsonString, IncompleteBusinessActivity.class);

        // then
        Assert.assertEquals("11111", actual.getOrderId());
        Assert.assertEquals("SENDING_CELL_PHONE", actual.getActivity());

    }

}
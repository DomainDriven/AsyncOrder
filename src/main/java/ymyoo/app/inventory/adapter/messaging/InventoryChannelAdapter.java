package ymyoo.app.inventory.adapter.messaging;

import com.google.gson.Gson;
import ymyoo.app.inventory.domain.InventoryService;
import ymyoo.app.inventory.domain.TakingOrderItem;
import ymyoo.messaging.Message;

/**
 * Created by 유영모 on 2017-01-02.
 */
public class InventoryChannelAdapter {

    public void checkAndReserve(Message message) {
        // 메시지를 변환
        TakingOrderItem takingOrderItem = InventoryChannelAdapter.CheckingInventoryMessageTranslator.translate(message.getBody());

        // 서비스 호출
        InventoryService inventoryService = new InventoryService();
        inventoryService.checkAndReserve(takingOrderItem);
    }

    static class CheckingInventoryMessageTranslator {
        public static TakingOrderItem translate(String data) {
            return new Gson().fromJson(data, TakingOrderItem.class);
        }
    }
}


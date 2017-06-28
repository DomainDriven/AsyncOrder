package ymyoo.app.inventory;

import ymyoo.app.inventory.adapter.messaging.InventoryReplier;

/**
 * Created by yooyoung-mo on 2017. 6. 23..
 */
public class InventoryApplication {
  public static void main(String[] args) {
    Thread inventoryReplier = new Thread(new InventoryReplier());
    inventoryReplier.start();
  }
}

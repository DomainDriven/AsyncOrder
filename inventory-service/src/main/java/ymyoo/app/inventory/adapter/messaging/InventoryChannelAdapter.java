package ymyoo.app.inventory.adapter.messaging;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import ymyoo.app.inventory.domain.InventoryService;
import ymyoo.app.inventory.domain.TakingOrderItem;
import ymyoo.infra.messaging.Message;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by 유영모 on 2017-01-02.
 */
public class InventoryChannelAdapter {

    public void checkAndReserve(Message message) {
        // 메시지를 변환
        TakingOrderItem takingOrderItem = null;
        try {
            takingOrderItem = CheckingInventoryMessageTranslator.translate((Map)message.getBody());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // 서비스 호출
        InventoryService inventoryService = new InventoryService();
        inventoryService.checkAndReserve(takingOrderItem);
    }

    static class CheckingInventoryMessageTranslator {
        public static TakingOrderItem translate(Map data) throws InvocationTargetException, IllegalAccessException {
            TakingOrderItem takingOrderItem = new TakingOrderItem();

            BeanUtilsBean beanUtilsBean = new BeanUtilsBean(new ConvertUtilsBean() {
                @Override
                public Object convert(String value, Class clazz) {
                    if (clazz.isEnum()){
                        return Enum.valueOf(clazz, value);
                    }else{
                        return super.convert(value, clazz);
                    }
                }
            });

            beanUtilsBean.populate(takingOrderItem, data);

            return takingOrderItem;
        }
    }
}


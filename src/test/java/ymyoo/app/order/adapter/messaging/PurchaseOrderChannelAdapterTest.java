package ymyoo.app.order.adapter.messaging;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ymyoo.messaging.core.MessageProducer;
import ymyoo.app.order.domain.po.*;
import ymyoo.app.order.domain.OrderItemDeliveryType;
import ymyoo.app.order.domain.OrderIdGenerator;
import ymyoo.messaging.core.Requester;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by 유영모 on 2016-12-16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(PurchaseOrderChannelAdapter.class)
public class PurchaseOrderChannelAdapterTest {
    @Test
    public void onPurchaseOrderCreated() throws Exception {
        // given
        Requester requester = PowerMockito.mock(Requester.class);













        String orderId = OrderIdGenerator.generate();
        PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem("prd-12345", 3, OrderItemDeliveryType.AGENCY);
        PurchaseOrderPayment purchaseOrderPayment = new PurchaseOrderPayment("t1234");
        Purchaser purchaser = new Purchaser("유영모", "010-0000-0000");
        PurchaseOrder purchaseOrder = PurchaseOrderFactory.create(orderId,purchaser, purchaseOrderItem, purchaseOrderPayment);

        MessageProducer mockMessageProducer = mock(MessageProducer.class);
        String messageId = java.util.UUID.randomUUID().toString().toUpperCase();

        PurchaseOrderChannelAdapter channelAdapter = new PurchaseOrderChannelAdapter();
  //      channelAdapter.setMessageProducer(mockMessageProducer);

        // when
   //     channelAdapter.onPurchaseOrderCreated(messageId, purchaseOrder);

        // then
        verify(mockMessageProducer).send(messageId, new Gson().toJson(purchaseOrder));
    }

}
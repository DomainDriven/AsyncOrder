package ymyoo.app.order.infrastructure.repository;

import org.junit.Assert;
import org.junit.Test;
import ymyoo.app.order.domain.command.OrderIdGenerator;
import ymyoo.app.order.domain.command.OrderItemDeliveryType;
import ymyoo.app.order.domain.command.po.PurchaseOrder;
import ymyoo.app.order.domain.command.po.PurchaseOrderItem;
import ymyoo.app.order.domain.command.po.PurchaseOrderPayment;
import ymyoo.app.order.domain.command.po.Purchaser;

/**
 * Created by yooyoung-mo on 2017. 6. 22..
 */
public class PurchaseOrderRepositoryTest extends AbstractOrderJpaRepository {

  @Test
  public void add() throws Exception {
    // given
    Purchaser purchaser = new Purchaser("유영모", "010-0000-0000", "gigamadness@gmail.com");
    PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem("P01234", 2, OrderItemDeliveryType.DIRECTING);
    PurchaseOrderPayment purchaseOrderPayment = new PurchaseOrderPayment("T11111", 2000, "11022-333333");

    PurchaseOrder po = new PurchaseOrder(OrderIdGenerator.generate(),
            purchaser, purchaseOrderItem, purchaseOrderPayment);

    // when
    PurchaseOrderRepository repository = new PurchaseOrderRepository();
    repository.add(po);

    // then
    PurchaseOrder actual = jpaTemplate.execute((em) -> {
      return em.find(PurchaseOrder.class, po.getOrderId());
    });

    Assert.assertEquals(po.getOrderId(), actual.getOrderId());

    Assert.assertEquals(po.getPurchaser().getContactNumber(), actual.getPurchaser().getContactNumber());
    Assert.assertEquals(po.getPurchaser().getName(), actual.getPurchaser().getName());
    Assert.assertEquals(po.getPurchaser().getEmail(), actual.getPurchaser().getEmail());

    Assert.assertEquals(po.getOrderItem().getProductId(), actual.getOrderItem().getProductId());
    Assert.assertEquals(po.getOrderItem().getDeliveryType(), actual.getOrderItem().getDeliveryType());
    Assert.assertEquals(po.getOrderItem().getOrderQty(), actual.getOrderItem().getOrderQty());

    Assert.assertEquals(po.getOrderPayment().getTid(), actual.getOrderPayment().getTid());
    Assert.assertEquals(po.getOrderPayment().getOrderAmount(), actual.getOrderPayment().getOrderAmount());
    Assert.assertEquals(po.getOrderPayment().getCreditCardNo(), actual.getOrderPayment().getCreditCardNo());
  }
}
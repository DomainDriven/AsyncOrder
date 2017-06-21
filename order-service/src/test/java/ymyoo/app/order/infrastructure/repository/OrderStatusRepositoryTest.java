package ymyoo.app.order.infrastructure.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ymyoo.app.order.domain.command.OrderStatus;

/**
 * Created by yooyoung-mo on 2017. 6. 22..
 */
public class OrderStatusRepositoryTest extends AbstractOrderJpaRepository {

  String orderId = java.util.UUID.randomUUID().toString().toUpperCase();


  @Before
  public void setUp() throws Exception {
    OrderStatus fixtureOrderStatus = getOrderStatusTestFixture();
    jpaTemplate.execute((em) -> {
      em.persist(fixtureOrderStatus);
      return null;
    });
  }

  private OrderStatus getOrderStatusTestFixture() {
    return new OrderStatus(orderId, OrderStatus.Status.INVENTORY_CHECKED);
  }

  @Test
  public void find() throws Exception {
    // given
    OrderStatus expectedOrderStatus = new OrderStatus(orderId, OrderStatus.Status.INVENTORY_CHECKED);

    // when
    OrderStatusRepository repository = new OrderStatusRepository();
    OrderStatus actualOrderStatus = repository.find(orderId);

    // then
    Assert.assertEquals(expectedOrderStatus.getOrderId(), actualOrderStatus.getOrderId());
    Assert.assertEquals(expectedOrderStatus.getStatus(), actualOrderStatus.getStatus());
  }
}
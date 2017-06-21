package ymyoo.infra.messaging.processor.repository;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import ymyoo.infra.messaging.processor.entitiy.OrderStatusEntity;
import ymyoo.infra.messaging.processor.entitiy.OrderStatusHistory;
import ymyoo.infra.persistence.GlobalEntityManagerFactory;

import javax.persistence.EntityManager;
import java.util.Date;

/**
 * Created by 유영모 on 2017-01-13.
 */
public class OrderStatusEntityRepositoryTest {

    @AfterClass
    public static void tearDownAfterClass() {
        GlobalEntityManagerFactory.closeEntityManagerFactory();
    }

    @Test
    public void add() throws Exception {
        // given
        String orderId = java.util.UUID.randomUUID().toString().toUpperCase();
        OrderStatusEntity orderStatusEntity = getOrderStatus(orderId, OrderStatusEntity.Status.INVENTORY_CHECKED);


        // when
        OrderStatusEntityRepository repository = new OrderStatusEntityRepository();
        repository.add(orderStatusEntity);

        // then
        EntityManager em = GlobalEntityManagerFactory.getEntityManagerFactory().createEntityManager();
        OrderStatusEntity actual = em.find(OrderStatusEntity.class, orderStatusEntity.getOrderId());


        Assert.assertEquals(orderStatusEntity.getOrderId(), actual.getOrderId());
        Assert.assertEquals(orderStatusEntity.getStatus(), actual.getStatus());
        Assert.assertEquals(1, actual.getHistories().size());
        Assert.assertEquals(orderStatusEntity.getStatus(), actual.getHistories().get(0).getStatus());
        Assert.assertNotNull(actual.getHistories().get(0).getCreatedDate());

        em.close();
    }

    @Test
    public void add_같은_아이디로_두번_추가_하였을때() {
        // given
        String orderId = java.util.UUID.randomUUID().toString().toUpperCase();
        OrderStatusEntity inventoryCheckedOrderStatus = getOrderStatus(orderId, OrderStatusEntity.Status.INVENTORY_CHECKED);
        OrderStatusEntity paymentDoneOrderStatus = getOrderStatus(orderId, OrderStatusEntity.Status.PAYMENT_DONE);

        // when
        OrderStatusEntityRepository repository = new OrderStatusEntityRepository();
        repository.add(inventoryCheckedOrderStatus);
        repository.add(paymentDoneOrderStatus);

        // then
        EntityManager em = GlobalEntityManagerFactory.getEntityManagerFactory().createEntityManager();
        OrderStatusEntity actual = em.find(OrderStatusEntity.class, orderId);


        Assert.assertEquals(orderId, actual.getOrderId());
        Assert.assertEquals(paymentDoneOrderStatus.getStatus(), actual.getStatus());
        Assert.assertEquals(2, actual.getHistories().size());
        Assert.assertEquals(inventoryCheckedOrderStatus.getStatus(), actual.getHistories().get(0).getStatus());
        Assert.assertEquals(inventoryCheckedOrderStatus.getStatus(), actual.getHistories().get(0).getStatus());
        Assert.assertEquals(paymentDoneOrderStatus.getStatus(), actual.getHistories().get(1).getStatus());
        Assert.assertNotNull(actual.getHistories().get(1).getCreatedDate());

        em.close();
    }

    private OrderStatusEntity getOrderStatus(String orderId, OrderStatusEntity.Status status) {
        OrderStatusHistory history = new OrderStatusHistory();
        history.setStatus(status);
        history.setCreatedDate(new Date());

        OrderStatusEntity orderStatusEntity = new OrderStatusEntity();
        orderStatusEntity.setOrderId(orderId);
        orderStatusEntity.setStatus(status);
        orderStatusEntity.addHistory(history);

        return orderStatusEntity;
    }

}
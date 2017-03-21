package ymyoo.app.order.infrastructure.repository;

import org.junit.*;
import ymyoo.app.order.domain.command.OrderItemDeliveryType;
import ymyoo.app.order.domain.command.po.PurchaseOrder;
import ymyoo.app.order.domain.command.po.PurchaseOrderItem;
import ymyoo.app.order.domain.command.po.PurchaseOrderPayment;
import ymyoo.app.order.domain.command.po.Purchaser;
import ymyoo.app.order.domain.query.dto.CustomerPurchaseOrder;
import ymyoo.persistence.GlobalEntityManagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Created by 유영모 on 2017-03-21.
 */
public class CustomerPurchaseOrderRepositoryTest {
    String orderId = java.util.UUID.randomUUID().toString().toUpperCase();
    PurchaseOrder poTestFixture = null;

    @AfterClass
    public static void tearDownAfterClass() {
        GlobalEntityManagerFactory.getEntityManagerFactory().close();
    }

    @Before
    public void setUp() throws Exception {
        poTestFixture = getPurchaseOrderTestFixture();

        EntityManager em = GlobalEntityManagerFactory.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(poTestFixture);

        transaction.commit();
        em.close();
    }

    private PurchaseOrder getPurchaseOrderTestFixture() {
        Purchaser purchaser = new Purchaser("유영모", "010-0000-0000", "gigamadness@gmail.com");
        PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem("P01234", 2, OrderItemDeliveryType.DIRECTING);
        PurchaseOrderPayment purchaseOrderPayment = new PurchaseOrderPayment("T11111", 2000, "11022-333333");

        return new PurchaseOrder(orderId,
                purchaser, purchaseOrderItem, purchaseOrderPayment);
    }

    @After
    public void tearDown() throws Exception {
        EntityManager em = GlobalEntityManagerFactory.getEntityManagerFactory().createEntityManager();

        PurchaseOrder po = em.find(PurchaseOrder.class, orderId);

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.remove(po);

        transaction.commit();
        em.close();
    }

    @Test
    public void findByOrderId() throws Exception {
        // given
        CustomerPurchaseOrderRepository repository = new CustomerPurchaseOrderRepository();

        // when
        CustomerPurchaseOrder customerPurchaseOrder = repository.findByOrderId(orderId);

        // then
        Assert.assertEquals(poTestFixture.getOrderId(), customerPurchaseOrder.getOrderId());
        Assert.assertEquals(poTestFixture.getOrderPayment().getCreditCardNo(), customerPurchaseOrder.getCreditCardNo());
        Assert.assertEquals(poTestFixture.getOrderPayment().getOrderAmount(), customerPurchaseOrder.getOrderAmount());
        Assert.assertEquals(poTestFixture.getOrderItem().getOrderQty(), customerPurchaseOrder.getOrderQty());
        Assert.assertEquals(poTestFixture.getOrderItem().getProductId(), customerPurchaseOrder.getProductId());
    }

}
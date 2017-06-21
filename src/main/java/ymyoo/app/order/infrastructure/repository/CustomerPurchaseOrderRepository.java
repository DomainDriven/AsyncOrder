package ymyoo.app.order.infrastructure.repository;

import ymyoo.app.order.domain.query.dto.CustomerPurchaseOrder;
import ymyoo.infra.persistence.TransactionJpaTemplate;

import javax.persistence.Query;

/**
 * Created by 유영모 on 2017-03-21.
 */
public class CustomerPurchaseOrderRepository {
    public CustomerPurchaseOrder findByOrderId(final String orderId) {
        TransactionJpaTemplate jpaTemplate = new TransactionJpaTemplate();
        final String sql = "SELECT ORDER_ID, PRODUCT_ID, ORDER_QTY, ORDER_AMOUNT, CREDIT_CARD_NO FROM VIEW_CUSTOMER_PURCHASE_ORDER WHERE ORDER_ID = ?";

        return jpaTemplate.execute((em)-> {
            Query nativeQuery = em.createNativeQuery(sql).setParameter(1, orderId);
            Object[] result = (Object[])nativeQuery.getSingleResult();

            return new CustomerPurchaseOrder((String)result[0], (String)result[1], (Integer)result[2], (Integer)result[3], (String)result[4]);
        });
    }
}

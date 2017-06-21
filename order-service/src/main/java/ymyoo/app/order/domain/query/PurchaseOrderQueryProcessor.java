package ymyoo.app.order.domain.query;


import ymyoo.app.order.domain.query.dto.CustomerPurchaseOrder;
import ymyoo.app.order.infrastructure.repository.CustomerPurchaseOrderRepository;

/**
 * Created by 유영모 on 2017-03-13.
 */
public class PurchaseOrderQueryProcessor {
    CustomerPurchaseOrderRepository repository = new CustomerPurchaseOrderRepository();

    public CustomerPurchaseOrder getCustomerPurchaseOrder(String orderId) {
        return repository.findByOrderId(orderId);
    }
}

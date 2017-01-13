package ymyoo.messaging.processor.order.status;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by 유영모 on 2017-01-13.
 */
@Embeddable
public class OrderStatusHistoryId implements Serializable {
    private String orderId;
    private OrderStatusEntity.Status status;

    public OrderStatusHistoryId(String orderId, OrderStatusEntity.Status status) {
        this.orderId = orderId;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public OrderStatusEntity.Status getStatus() {
        return status;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

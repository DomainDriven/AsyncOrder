package ymyoo.app.order.domain.command;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by 유영모 on 2017-01-11.
 */
@Entity
public class OrderStatus {
    public enum Status {
        INVENTORY_CHECKED_REQUEST,
        INVENTORY_CHECKED,
        PAYMENT_REQUEST,
        PAYMENT_DONE,
        PURCHASE_ORDER_CREATED,
        ORDER_FAILED
    }

    @Id
    private String orderId;

    private Status status;

    public OrderStatus() {
    }

    public OrderStatus(String orderId, Status status) {
        this.orderId = orderId;
        this.status = status;
    }


    public String getOrderId() {
        return orderId;
    }

    public Status getStatus() {
        return status;
    }
}

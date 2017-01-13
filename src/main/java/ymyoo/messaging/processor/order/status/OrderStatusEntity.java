package ymyoo.messaging.processor.order.status;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by 유영모 on 2017-01-11.
 * JPA 에서 같은 이름의 Entity 가 하나이어야 하는 제약에 따라 같은 테이블과 매핑되지만 다르게 작명
 */
@Entity
@Table(name="OrderStatus")
public class OrderStatusEntity {
    public enum Status {
        SALE_ORDER_CREATED,
        INVENTORY_CHECKED,
        PAYMENT_DONE,
        PURCHASE_ORDER_CREATED,
        ORDER_FAILED
    }

    @Id
    private String orderId;

    private Status status;

    public OrderStatusEntity() {
    }

    public OrderStatusEntity(String orderId, Status status) {
        this.orderId = orderId;
        this.status = status;
    }


    public String getOrderId() {
        return orderId;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "OrderStatusEntity{" +
                "orderId='" + orderId + '\'' +
                ", status=" + status +
                '}';
    }
}

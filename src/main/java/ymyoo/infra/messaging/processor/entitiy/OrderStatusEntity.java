package ymyoo.infra.messaging.processor.entitiy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 유영모 on 2017-01-11.
 * JPA 에서 같은 이름의 Entity 가 하나이어야 하는 제약에 따라 같은 테이블과 매핑되지만 다르게 작명
 */
@Entity
@Table(name="OrderStatus")
public class OrderStatusEntity {
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

    @OneToMany(mappedBy = "orderStatusEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<OrderStatusHistory> histories = new ArrayList<>();

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<OrderStatusHistory> getHistories() {
        return histories;
    }

    public void setHistories(List<OrderStatusHistory> histories) {
        this.histories = histories;
    }

    public OrderStatusEntity() {
    }

    public OrderStatusEntity(String orderId, Status status) {
        this.orderId = orderId;
        this.status = status;
    }

    public void addHistory(OrderStatusHistory history) {
        this.getHistories().add(history);
        if(history.getOrderStatusEntity() != this) {
            history.setOrderStatusEntity(this);
        }
    }

    @Override
    public String toString() {
        return "OrderStatusEntity{" +
                "orderId='" + orderId + '\'' +
                ", status=" + status +
                '}';
    }
}

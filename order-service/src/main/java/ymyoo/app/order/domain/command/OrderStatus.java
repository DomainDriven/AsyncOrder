package ymyoo.app.order.domain.command;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yooyoung-mo on 2017. 6. 23..
 */
@Entity
@Table(name="ORDER_STATUS")
public class OrderStatus {
    public enum Status {
      INVENTORY_REQUEST,
      INVENTORY_CHECKED,
      PAYMENT_REQUEST,
      PAYMENT_DONE,
      PURCHASE_ORDER_REQUEST,
      PURCHASE_ORDER_CREATED,
      ORDER_FAILED,
      ORDER_READY
    }

    @Id
    private String orderId;

    private Status status;

    @OneToMany(mappedBy = "orderStatus", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<OrderStatusHistory> histories = new ArrayList<>();

    public OrderStatus() {
    }

    public OrderStatus(String orderId, Status status) {
        this.orderId = orderId;
        this.status = status;
    }

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

    public void addHistory(OrderStatusHistory history) {
        this.getHistories().add(history);
        if(history.getOrderStatus() != this) {
            history.setOrderStatus(this);
        }
    }

}

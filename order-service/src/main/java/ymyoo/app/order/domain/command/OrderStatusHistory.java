package ymyoo.app.order.domain.command;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yooyoung-mo on 2017. 7. 4..
 */
@Entity
public class OrderStatusHistory {

    @Id
    @GeneratedValue
    private Long id;

    private OrderStatus.Status status;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private OrderStatus orderStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus.Status getStatus() {
        return status;
    }

    public void setStatus(OrderStatus.Status status) {
        this.status = status;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatusEntity) {
        this.orderStatus = orderStatusEntity;

        if(!orderStatusEntity.getHistories().contains(this)) {
            orderStatusEntity.getHistories().add(this);
        }
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}

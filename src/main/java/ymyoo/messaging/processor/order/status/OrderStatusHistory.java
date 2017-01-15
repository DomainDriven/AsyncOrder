package ymyoo.messaging.processor.order.status;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 유영모 on 2017-01-13.
 * 주문 상태 이력
 */
@Entity
public class OrderStatusHistory {

    @Id
    @GeneratedValue
    private Long id;

    private OrderStatusEntity.Status status;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private OrderStatusEntity orderStatusEntity;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatusEntity.Status getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEntity.Status status) {
        this.status = status;
    }

    public OrderStatusEntity getOrderStatusEntity() {
        return orderStatusEntity;
    }

    public void setOrderStatusEntity(OrderStatusEntity orderStatusEntity) {
        this.orderStatusEntity = orderStatusEntity;

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

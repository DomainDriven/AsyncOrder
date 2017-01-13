package ymyoo.messaging.processor.order.status;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 유영모 on 2017-01-13.
 * 주문 상태 이력
 */
@Entity
public class OrderStatusHistory {
    @EmbeddedId
    private OrderStatusHistoryId id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private OrderStatusEntity orderStatusEntity;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public OrderStatusHistory() {
    }

    public OrderStatusHistory(OrderStatusHistoryId id, Date createdDate) {
        this.id = id;
        this.createdDate = createdDate;
    }

    public OrderStatusHistoryId getId() {
        return id;
    }


    public OrderStatusEntity getOrderStatusEntity() {
        return orderStatusEntity;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
}

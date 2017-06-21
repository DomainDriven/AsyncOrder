package ymyoo.infra.messaging.processor.entitiy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by 유영모 on 2017-01-24.
 */
@Entity
public class IncompleteBusinessActivity {
    @Id @GeneratedValue
    private Long id;

    private String orderId;

    private String activity;

    public IncompleteBusinessActivity() {
    }

    public IncompleteBusinessActivity(String orderId, String activity) {
        this.orderId = orderId;
        this.activity = activity;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getActivity() {
        return activity;
    }

    @Override
    public String toString() {
        return "IncompleteBusinessActivity{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", activity='" + activity + '\'' +
                '}';
    }
}

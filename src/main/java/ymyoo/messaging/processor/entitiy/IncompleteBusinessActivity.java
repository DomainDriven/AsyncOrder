package ymyoo.messaging.processor.entitiy;

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

    public String getOrderId() {
        return orderId;
    }

    public String getActivity() {
        return activity;
    }
}

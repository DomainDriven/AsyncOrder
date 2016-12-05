package spike;

/**
 * Created by 유영모 on 2016-12-05.
 */
public class KafkaMessageBody {
    private String orderId;

    private String productName;

    public KafkaMessageBody(String orderId, String productName) {
        this.orderId = orderId;
        this.productName = productName;
    }


    public String getOrderId() {
        return orderId;
    }

    public String getProductName() {
        return productName;
    }
}

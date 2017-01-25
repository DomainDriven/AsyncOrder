package ymyoo.app.notification.domain;

/**
 * Created by 유영모 on 2017-01-25.
 */
public class PurchaseNotification {
    private String orderId;
    private String email;
    private String cellPhone;

    public PurchaseNotification(String orderId, String email, String cellPhone) {
        this.orderId = orderId;
        this.email = email;
        this.cellPhone = cellPhone;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getEmail() {
        return email;
    }

    public String getCellPhone() {
        return cellPhone;
    }
}

package ymyoo.app.notification.domain;

/**
 * Created by 유영모 on 2017-01-25.
 */
public class PurchaseNotification {
    private String orderId;
    private String email;
    private String cellPhone;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    @Override
    public String toString() {
        return "PurchaseNotification{" +
                "orderId='" + orderId + '\'' +
                ", email='" + email + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                '}';
    }
}

package ymyoo.app.payment.domain;

/**
 * Created by 유영모 on 2016-11-25.
 */
public class TakingOrderPayment {
    private String orderId;
    /** 결제 금액 **/
    private int orderAmount = 0;

    /** 신용 카드 번호 **/
    private String creditCardNo;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }

    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }
}

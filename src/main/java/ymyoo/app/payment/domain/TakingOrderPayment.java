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


    public TakingOrderPayment(String orderId, int orderAmount, String creditCardNo) {
        this.orderId = orderId;
        this.orderAmount = orderAmount;
        this.creditCardNo = creditCardNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }
}

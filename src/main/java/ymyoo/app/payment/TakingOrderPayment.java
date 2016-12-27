package ymyoo.app.payment;

/**
 * Created by 유영모 on 2016-11-25.
 */
public class TakingOrderPayment {
    /** 결제 금액 **/
    private int orderAmount = 0;

    /** 신용 카드 번호 **/
    private String creditCardNo;

    public TakingOrderPayment(int orderAmount, String creditCardNo) {
        this.orderAmount = orderAmount;
        this.creditCardNo = creditCardNo;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }
}

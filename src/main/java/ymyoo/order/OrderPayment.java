package ymyoo.order;

/**
 * 주문 결제
 *
 * Created by 유영모 on 2016-10-07.
 */
public class OrderPayment {
    /** 결제 금액 **/
    private int orderAmount = 0;

    /** 신용 카드 번호 **/
    private String creditCardNo;

    public OrderPayment(int orderAmount, String creditCardNo) {
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

package ymyoo.app.order.domain.command;

/**
 * 사용자가 입력한 결제 정보
 * 사용자가 주문한 정보는 주문 처리 과정에서 변하지 않아야 하므로 Immutable 클래스로..
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

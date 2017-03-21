package ymyoo.app.order.domain.command.po;

import javax.persistence.*;

/**
 * Created by 유영모 on 2016-12-15.
 */

@Entity
@Table(name = "PURCHASE_ORDER_PAYMENT")
public class PurchaseOrderPayment {

    @Id @GeneratedValue
    @Column(name = "PURCHASE_ORDER_PAYMENT_ID")
    private Long id;

    /** 승인 아이디 **/
    private String tid;

    /** 결제 금액 **/
    @Column(name = "ORDER_AMOUNT")
    private int orderAmount = 0;

    /** 신용 카드 번호 **/
    @Column(name = "CREDIT_CARD_NO")
    private String creditCardNo;


    public PurchaseOrderPayment() {
    }

    public PurchaseOrderPayment(String tid, int orderAmount, String creditCardNo) {
        this.tid = tid;
        this.orderAmount = orderAmount;
        this.creditCardNo = creditCardNo;
    }

    public Long getId() {
        return id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }
}

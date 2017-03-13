package ymyoo.app.order.domain.command.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by 유영모 on 2016-12-15.
 */

@Entity
public class PurchaseOrderPayment {

    @Id @GeneratedValue
    @Column(name = "purchaseOrderPaymentId")
    private Long id;

    /** 승인 아이디 **/
    private String tid;

    /** 결제 금액 **/
    private int orderAmount = 0;

    /** 신용 카드 번호 **/
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

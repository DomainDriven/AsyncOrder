package ymyoo.app.payment.domain;

/**
 * 결제 후 결제사로 부터 반환 받은 승인 정보
 *
 * Created by 유영모 on 2016-10-10.
 */
public class ApprovalPayment {
    /** 승인 아이디 **/
    private String tid;

    private String orderId;

    public ApprovalPayment(String tid, String orderId) {
        this.tid = tid;
        this.orderId = orderId;
    }

    public String getTid() {
        return tid;
    }

    public String getOrderId() {
        return orderId;
    }
}

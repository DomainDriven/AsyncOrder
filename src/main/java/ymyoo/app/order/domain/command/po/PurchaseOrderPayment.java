package ymyoo.app.order.domain.command.po;

/**
 * Created by 유영모 on 2016-12-15.
 */
public class PurchaseOrderPayment {
    /** 승인 아이디 **/
    private String tid;

    public PurchaseOrderPayment(String tid) {
        this.tid = tid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}

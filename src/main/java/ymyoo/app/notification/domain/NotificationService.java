package ymyoo.app.notification.domain;

import ymyoo.utility.PrettySystemOut;

/**
 * Created by 유영모 on 2017-01-24.
 */
public class NotificationService {
    public void sendEmail(PurchaseNotification purchaseNotification) {
        PrettySystemOut.println(this.getClass(), purchaseNotification.getEmail() + " Email 전송 완료");
    }

    public void sendMMS(PurchaseNotification purchaseNotification) throws Exception {
        if(purchaseNotification.getCellPhone().equals("010-0000-0000")) {
            throw new Exception("SENDING_CELL_PHONE");
        }

        PrettySystemOut.println(this.getClass(), purchaseNotification.getCellPhone() + " 휴대 전화 문자 전송 완료");
    }
}

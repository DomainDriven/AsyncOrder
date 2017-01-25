package ymyoo.app.notification.domain;

/**
 * Created by 유영모 on 2017-01-24.
 */
public class NotificationService {
    public void sendEmail(String email) {
        System.out.println(email + "Email 전송 완료");
    }

    public void sendMMS(String cellPhoneNo) throws Exception {
        if(cellPhoneNo.equals("010-0000-0000")) {
            throw new Exception("휴대 전화 문자 전송 실패..");
        }
        System.out.println(cellPhoneNo + "휴대 전화 문자 전송 완료");
    }
}

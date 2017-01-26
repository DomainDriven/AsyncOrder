package ymyoo.app.order.domain;

/**
 * 주문자
 *
 * Created by 유영모 on 2016-12-15.
 */
public class Orderer {
    /**
     * 주문자명
     */
    private String name;

    /**
     * 연락처 전화번호
     */
    private String contactNumber;

    private String email;

    public Orderer(String name, String contactNumber, String email) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }
}

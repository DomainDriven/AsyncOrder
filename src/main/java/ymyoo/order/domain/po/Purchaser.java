package ymyoo.order.domain.po;

/**
 * 구매자
 * Created by 유영모 on 2016-12-15.
 */
public class Purchaser {
    /**
     * 주문자명
     */
    private String name;

    /**
     * 연락처 전화번호
     */
    private String contactNumber;

    public Purchaser(String name, String contactNumber) {
        this.name = name;
        this.contactNumber = contactNumber;
    }

    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }
}

package ymyoo.app.order.domain.command.po;

import javax.persistence.*;

/**
 * 구매자
 * Created by 유영모 on 2016-12-15.
 */
@Entity
public class Purchaser {

    @Id @GeneratedValue
    @Column(name = "PURCHASER_ID")
    private Long id;

    /**
     * 주문자명
     */
    private String name;

    /**
     * 연락처 전화번호
     */
    private String contactNumber;

    private String email;

    public Purchaser() {
    }

    public Purchaser(String name, String contactNumber, String email) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public Long getId() {
        return id;
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

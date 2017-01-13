package ymyoo.app.order.domain;

/**
 * 주문 아이디 생성기
 *
 * Created by 유영모 on 2016-10-07.
 */
public class OrderIdGenerator {
    public static String generate() {
        return java.util.UUID.randomUUID().toString().toUpperCase();
    }
}
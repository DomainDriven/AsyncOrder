package ymyoo.app.order.domain.so;

/**
 * 주문 아이디 생성기
 *
 * Created by 유영모 on 2016-10-07.
 */
public class SalesOrderIdGenerator {
    public static String generate() {
        return java.util.UUID.randomUUID().toString().toUpperCase();
    }
}

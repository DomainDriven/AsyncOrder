package ymyoo.order;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import ymyoo.order.event.OrderCompleteEvent;

/**
 * Created by 유영모 on 2016-10-07.
 */
public class OrderTest {

    /**
     * 비동기 주문 하기 테스트
     *
     * [가정]
     *  사용자가 상품을 선택, 주문서에서 배송지, 할인, 결제 수단을 모두 입력 하고 '주문' 요청을 했다고 가정 한다.
     * [제약 조건]
     *  구현 복잡도를 낮추기 위해서 Process 수준에서 비동기 처리를 구현 한다.
     */
    @Test
    public void testPlaceOrder() throws Exception {
        System.out.println("<Client> 시작...");

        // Given
        Order order = OrderFactory.create(new OrderItem("prd-123", 2),  new OrderPayment(2000, "123-456-0789"));
        // 주문 완료 이벤트 수신자
        MockOrderCompleteEventListener mockListener = new MockOrderCompleteEventListener();

        // When
        // 주문 하기!!
        String orderId = order.placeOrder(mockListener);
        System.out.println("<Client> 주문 아이디 반환 받음 - 주문 아이디 : " + orderId);

        // Then
        // 주문 ID 반환 확인(Synchronized)
        Assert.assertTrue(StringUtils.isNotBlank(orderId));

        // 비동기 처리 대기
        synchronized (mockListener) {
            mockListener.wait(6000);
        }


        // 비동기 처리 확인
        OrderCompleteEvent event = mockListener.getOrderCompleteEvent();
        // 반환 받은 주문 ID로 검증
        Assert.assertEquals(orderId, event.getOrderId());
        System.out.println("<Client> 주문 완료 이벤트 수신 - 주문 아이디 : " + orderId);
        System.out.println("<Client> 종료...");
    }
}

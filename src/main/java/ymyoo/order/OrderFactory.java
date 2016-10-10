package ymyoo.order;

/**
 * 결제 전 주문(Order)을 만드는 팩토리
 * 주문할 상품, 배송지, 할인, 결제 수단등을 묶어 하나의 주문으로 만드는 과정이 많으므로
 * 주문 생성에 대한 관심사를 분리하여 팩토리를 통해 생성 한다.
 *
 * Created by 유영모 on 2016-10-07.
 */
public class OrderFactory {
    static public Order create(OrderItem orderItem, OrderPayment orderPayment) {
        // 상품, 배송지, 할인, 결제 수단 등은 현재 검증 대상이 아니므로 Skip...
        return new Order(orderItem, orderPayment);
    }
}

package ymyoo.order.domain.so;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ymyoo.infra.messaging.remote.channel.MessageChannel;
import ymyoo.infra.messaging.remote.channel.MessageConsumer;
import ymyoo.order.domain.event.OrderCompleted;
import ymyoo.order.domain.event.OrderFailed;
import ymyoo.infra.messaging.local.EventPublisher;
import ymyoo.infra.messaging.local.EventSubscriber;
import ymyoo.infra.messaging.remote.channel.MessageBroker;

/**
 * Created by 유영모 on 2016-10-07.
 */
public class SalesOrderIntegrationTest {
    private String orderId;
    private boolean eventAccepted;

    Thread messageBroker = null;
    Thread inventoryReplyMessageConsumer = null;
    Thread paymentReplyMessageConsumer = null;

    @Before
    public void setUp() throws Exception {
        orderId = "";
        eventAccepted = false;

        // Message Consumer Subscribe
        messageBroker = new Thread(new MessageBroker(MessageChannel.INVENTORY_REQUEST, MessageChannel.PAYMENT_AUTH_APP_REQUEST, MessageChannel.PURCHASE_ORDER_CREATED));
        messageBroker.start();

        inventoryReplyMessageConsumer = new Thread(new MessageConsumer(MessageChannel.INVENTORY_REPLY));
        inventoryReplyMessageConsumer.start();

        paymentReplyMessageConsumer = new Thread(new MessageConsumer(MessageChannel.PAYMENT_AUTH_APP_REPLY));
        paymentReplyMessageConsumer.start();
    }

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
        // Given
        SalesOrder order = SalesOrderFactory.create(new Orderer("유영모", "010-0000-0000"),
                new SalesOrderItem("P0001", 2, OrderItemDeliveryType.AGENCY),
                new SalesOrderPayment(2000, "123-456-0789"));

        // 주문 완료 이벤트 구독
        EventSubscriber subscriber = new EventSubscriber<OrderCompleted>() {
            @Override
            public void handleEvent(OrderCompleted event) {
                // Then - 주문 완료 확인(Async)
                eventAccepted = true;
                Assert.assertEquals(orderId, event.getOrderId());
                System.out.println("<Client> 주문 완료 이벤트 수신 - 주문 아이디 : " + event.getOrderId());
            }

            @Override
            public Class<OrderCompleted> subscribedToEventType() {
                return OrderCompleted.class;
            }
        };

        EventPublisher.instance().subscribe(subscriber);

        // When
        // 주문 하기!!
        System.out.println("<Client> 주문 시작...");
        orderId = order.placeOrder();
        System.out.println("<Client> 주문 아이디 반환 받음 - 주문 아이디 : " + orderId);

        // Then - 동기 처리 확인
        // 주문 ID 반환 확인(Synchronized)
        Assert.assertTrue(StringUtils.isNotBlank(orderId));

        // 비동기 처리 대기
        synchronized (subscriber) {
            subscriber.wait(4000);
        }
        Assert.assertTrue("이벤트 미 수신", eventAccepted);

        System.out.println("<Client> 주문 종료...");
    }

    @Test
    public void testPlaceOrder_예외_재고_없음() throws Exception {
        // Given
        SalesOrder order = SalesOrderFactory.create(new Orderer("유영모", "010-0000-0000"),
                new SalesOrderItem("P0002", 2, OrderItemDeliveryType.AGENCY),
                new SalesOrderPayment(2000, "123-456-0789"));

        // 주문 실패 이벤트 구독
        EventSubscriber subscriber = new EventSubscriber<OrderFailed>() {
            @Override
            public void handleEvent(OrderFailed event) {
                // Then - 주문 실패 확인(Async)
                eventAccepted = true;
                System.out.println("<Client> 주문 실패 이벤트 수신 - 주문 아이디 : " + event.getOrderId());
                Assert.assertEquals(orderId, event.getOrderId());
                Assert.assertEquals("Stockout", event.getErrorMsg());
            }

            @Override
            public Class<OrderFailed> subscribedToEventType() {
                return OrderFailed.class;
            }
        };

        EventPublisher.instance().subscribe(subscriber);

        // When
        // 주문 하기!!
        System.out.println("<Client> 주문 시작...");
        orderId = order.placeOrder();
        System.out.println("<Client> 주문 아이디 반환 받음 - 주문 아이디 : " + orderId);

        // Then - 동기 처리 확인
        // 주문 ID 반환 확인(Synchronized)
        Assert.assertTrue(StringUtils.isNotBlank(orderId));

        // 비동기 처리 대기
        synchronized (subscriber) {
            subscriber.wait(4000);
        }
        Assert.assertTrue("이벤트 미 수신", eventAccepted);

        System.out.println("<Client> 주문 종료...");

    }

    @Test
    public void testPlaceOrder_자사배송상품() throws Exception {
        // Given
        SalesOrder order = SalesOrderFactory.create(new Orderer("유영모", "010-0000-0000"),
                new SalesOrderItem("P0003", 1, OrderItemDeliveryType.DIRECTING),
                new SalesOrderPayment(2000, "123-456-0789"));

        // 주문 완료 이벤트 구독
        EventSubscriber subscriber = new EventSubscriber<OrderCompleted>() {
            @Override
            public void handleEvent(OrderCompleted event) {
                // Then - 주문 완료 확인(Async)
                eventAccepted = true;
                Assert.assertEquals(orderId, event.getOrderId());
                System.out.println("<Client> 주문 완료 이벤트 수신 - 주문 아이디 : " + event.getOrderId());
            }

            @Override
            public Class<OrderCompleted> subscribedToEventType() {
                return OrderCompleted.class;
            }
        };

        EventPublisher.instance().subscribe(subscriber);

        // When
        System.out.println("<Client> 주문 시작...");
        orderId = order.placeOrder();
        System.out.println("<Client> 주문 아이디 반환 받음 - 주문 아이디 : " + orderId);

        // Then
        // 주문 ID 반환 확인(Synchronized)
        Assert.assertTrue(StringUtils.isNotBlank(orderId));

        // 비동기 처리 대기
        synchronized (subscriber) {
            subscriber.wait(10000);
        }
        Assert.assertTrue("이벤트 미 수신", eventAccepted);

        System.out.println("<Client> 주문 종료...");
    }
}

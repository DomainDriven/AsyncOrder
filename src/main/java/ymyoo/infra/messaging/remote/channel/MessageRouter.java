package ymyoo.infra.messaging.remote.channel;

import com.google.gson.Gson;
import ymyoo.inventory.InventoryService;
import ymyoo.inventory.TakingOrderItem;
import ymyoo.payment.ApprovalPayment;
import ymyoo.payment.PaymentGatewayService;
import ymyoo.payment.TakingOrderPayment;
import ymyoo.utility.PrettySystemOut;

import java.util.HashMap;
import java.util.Map;

/**
 * 컨텐츠 기반 메시지 라우터
 *
 * Created by 유영모 on 2016-11-29.
 */
public class MessageRouter {

    public static void route(String channel, String key, String data) {
        String messageId = extractMessageId(key);
        String replyChannel = extractReplyChannel(key);

        if(channel.equals(MessageChannel.INVENTORY_REQUEST)) {
            // 메시지를 변환
            TakingOrderItem takingOrderItem = CheckingInventoryMessageTranslator.translate(data);

            // 서비스 호출
            InventoryService inventoryService = new InventoryService();
            inventoryService.checkAndReserve(takingOrderItem);

            // 결과를 메시지로 전송
            Map<String, String> replyMessageBody = new HashMap<>();
            replyMessageBody.put("validation", "SUCCESS");

            MessageProducer producer = new MessageProducer(replyChannel, "");
            producer.send(messageId, new Gson().toJson(replyMessageBody));
        } else if(channel.equals(MessageChannel.PAYMENT_AUTH_APP_REQUEST)) {
            // 메시지 변환
            TakingOrderPayment takingOrderPayment = AuthApvPaymentMessageTranslator.translate(data);

            // 서비스 호출
            PaymentGatewayService paymentGatewayService = new PaymentGatewayService();
            ApprovalPayment approvalOrderPayment = paymentGatewayService.authenticateAndApproval(takingOrderPayment);

            // 결과를 메시지로 전송
            MessageProducer producer = new MessageProducer(replyChannel, "");
            producer.send(messageId, new Gson().toJson(approvalOrderPayment));
        } else if(channel.equals(MessageChannel.PURCHASE_ORDER_CREATED)) {
            // 구매 주문 생성에 따른 후속 처리
            PrettySystemOut.println(MessageRouter.class, "주문 완료 문자 발송");
        } else {
            throw new RuntimeException("라우팅 가능한 채널이 아닙니다.");
        }
    }

    static class CheckingInventoryMessageTranslator {
        public static TakingOrderItem translate(String data) {
            return new Gson().fromJson(data, TakingOrderItem.class);
        }
    }

    static class AuthApvPaymentMessageTranslator {
        public static TakingOrderPayment translate(String data) {
            return new Gson().fromJson(data, TakingOrderPayment.class);
        }
    }

    private static String extractReplyChannel(String str) {
        return str.substring(str.indexOf("::") + 2, str.length());
    }

    private static String extractMessageId(String str) {
        if(str.contains("::")) {
            return str.substring(0, str.indexOf("::"));
        } else {
            return str;
        }
    }
}

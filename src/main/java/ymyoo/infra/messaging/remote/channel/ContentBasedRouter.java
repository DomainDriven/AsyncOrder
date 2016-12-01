package ymyoo.infra.messaging.remote.channel;

import com.google.gson.Gson;
import ymyoo.infra.messaging.remote.channel.blockingqueue.ReplyBlockingQueue;
import ymyoo.infra.messaging.remote.channel.message.Message;
import ymyoo.infra.messaging.remote.channel.message.MessageHead;
import ymyoo.inventory.InventoryService;
import ymyoo.inventory.TakingOrderItem;
import ymyoo.payment.ApprovalPayment;
import ymyoo.payment.PaymentGatewayService;
import ymyoo.payment.TakingOrderPayment;

import java.util.HashMap;
import java.util.Map;

/**
 * 컨텐츠 기반 메시지 라우터
 *
 * Created by 유영모 on 2016-11-29.
 */
public class ContentBasedRouter {

    public void route(Message message) {
        if(message.getHead().getType() == MessageHead.MessageType.CHECK_INVENTOY) {
            // 메시지를 변환
            TakingOrderItem takingOrderItem = CheckingInventoryMessageTranslator.translate(message);

            // 서비스 호출
            InventoryService inventoryService = new InventoryService();
            inventoryService.checkAndReserve(takingOrderItem);

            // 결과를 메시지로 전송
            MessageProducer messageProducer = new MessageProducer(ReplyBlockingQueue.getBlockingQueue());
            Map<String, String> replyMessageBody = new HashMap<>();
            replyMessageBody.put("validation", "SUCCESS");

            messageProducer.send(new Message(message.getHead(), new Gson().toJson(replyMessageBody)));
        } else if(message.getHead().getType() == MessageHead.MessageType.AUTH_APV_PAYMENT) {
            // 메시지 변환
            TakingOrderPayment takingOrderPayment = AuthApvPaymentMessageTranslator.translate(message);

            // 서비스 호출
            PaymentGatewayService paymentGatewayService = new PaymentGatewayService();
            ApprovalPayment approvalOrderPayment = paymentGatewayService.authenticateAndApproval(takingOrderPayment);

            // 결과를 메시지로 전송
            MessageProducer messageProducer = new MessageProducer(ReplyBlockingQueue.getBlockingQueue());
            Map<String, String> replyMessageBody = new HashMap<>();
            replyMessageBody.put("tid", approvalOrderPayment.getTid());
            messageProducer.send(new Message(message.getHead(), new Gson().toJson(replyMessageBody)));
        }
    }

    static class CheckingInventoryMessageTranslator {
        public static TakingOrderItem translate(Message message) {
            return new Gson().fromJson(message.getBody(), TakingOrderItem.class);
        }
    }

    static class AuthApvPaymentMessageTranslator {
        public static TakingOrderPayment translate(Message message) {
            return new Gson().fromJson(message.getBody(), TakingOrderPayment.class);
        }
    }
}

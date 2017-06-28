package ymyoo.app.payment;

import ymyoo.app.payment.adapter.messaging.PaymentReplier;

/**
 * Created by yooyoung-mo on 2017. 6. 23..
 */
public class PaymentApplication {
  public static void main(String[] args) {
    Thread paymentReplier = new Thread(new PaymentReplier());
    paymentReplier.start();
  }
}

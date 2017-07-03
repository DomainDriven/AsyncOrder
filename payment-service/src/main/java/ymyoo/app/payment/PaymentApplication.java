package ymyoo.app.payment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import ymyoo.app.payment.adapter.messaging.PaymentReplier;

/**
 * Created by yooyoung-mo on 2017. 6. 23..
 */

@SpringBootApplication
public class PaymentApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            Thread paymentReplier = new Thread(new PaymentReplier());
            paymentReplier.start();
        };
    }
}

package ymyoo.app.order;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import ymyoo.infra.messaging.MessageChannels;
import ymyoo.infra.messaging.PollingMessageConsumer;

/**
 * Created by yooyoung-mo on 2017. 7. 4..
 */

@SpringBootApplication
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            Thread inventoryReplyMessageConsumer = new Thread(new PollingMessageConsumer(MessageChannels.INVENTORY_REPLY));
            inventoryReplyMessageConsumer.start();

            Thread paymentReplyMessageConsumer = new Thread(new PollingMessageConsumer(MessageChannels.PAYMENT_AUTH_APP_REPLY));
            paymentReplyMessageConsumer.start();
        };
    }
}

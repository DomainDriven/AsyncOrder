package ymyoo.app.notification;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import ymyoo.app.notification.adapter.messaging.NotificationMessageConsumer;
import ymyoo.infra.messaging.MessageChannels;

/**
 * Created by yooyoung-mo on 2017. 6. 23..
 */

@SpringBootApplication
public class NotificationApplication {
  public static void main(String[] args) {
      SpringApplication.run(NotificationApplication.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
      return args -> {
          Thread notificationMessageConsumer = new Thread(new NotificationMessageConsumer(MessageChannels.PURCHASE_ORDER_CREATED));
          notificationMessageConsumer.start();
      };
  }
}

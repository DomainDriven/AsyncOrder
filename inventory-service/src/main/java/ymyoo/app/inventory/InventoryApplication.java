package ymyoo.app.inventory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import ymyoo.app.inventory.adapter.messaging.InventoryReplier;

/**
 * Created by yooyoung-mo on 2017. 6. 23..
 */
@SpringBootApplication
public class InventoryApplication {
//    public static void main(String[] args) {
//        Thread inventoryReplier = new Thread(new InventoryReplier());
//        inventoryReplier.start();
//    }

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            Thread inventoryReplier = new Thread(new InventoryReplier());
            inventoryReplier.start();
        };
    }
}

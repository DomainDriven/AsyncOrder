package ymyoo.app.order.infrastructure;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by 유영모 on 2017-01-12.
 */
public class OrderEntityManagerFactory {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("order");

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    public static void closeEntityManagerFactory() {
        emf.close();
    }
}

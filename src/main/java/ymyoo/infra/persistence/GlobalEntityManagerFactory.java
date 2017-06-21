package ymyoo.infra.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by 유영모 on 2017-01-12.
 */
public class GlobalEntityManagerFactory {
    private static EntityManagerFactory emf = null;

    public static EntityManagerFactory getEntityManagerFactory() {
        if(emf == null || emf.isOpen() == false) {
            emf = Persistence.createEntityManagerFactory("order");
        }

        return emf;
    }

    public static void closeEntityManagerFactory() {
        emf.close();
    }
}

package ymyoo.infra.persistence;

import javax.persistence.EntityManager;

/**
 * Created by 유영모 on 2017-02-14.
 */
public interface ABlockOfCode <T> {
    T execute(EntityManager em);
}

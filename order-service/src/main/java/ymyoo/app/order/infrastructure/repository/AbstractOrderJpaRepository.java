package ymyoo.app.order.infrastructure.repository;

import ymyoo.infra.persistence.TransactionJpaTemplate;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by yooyoung-mo on 2017. 6. 22..
 */
public abstract class AbstractOrderJpaRepository {
  private static final String PERSISTENCE_UNIT_NAME = "order";
  private static EntityManagerFactory emf = null;

  protected TransactionJpaTemplate jpaTemplate;

  public AbstractOrderJpaRepository() {
    if(emf == null) {
      emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }
    jpaTemplate = new TransactionJpaTemplate(emf);
  }
}

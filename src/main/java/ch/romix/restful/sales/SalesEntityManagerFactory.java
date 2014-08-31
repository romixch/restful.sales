package ch.romix.restful.sales;

import java.io.Closeable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.glassfish.hk2.api.Factory;
import org.glassfish.jersey.server.CloseableService;

public class SalesEntityManagerFactory implements Factory<EntityManager> {
  static final ThreadLocal<EntityManager> ENTITY_MANAGERS = new ThreadLocal<>();

  private final CloseableService closeableService;
  EntityManagerFactory emf;

  @Inject
  public SalesEntityManagerFactory(CloseableService closeableService) {
    this.closeableService = closeableService;
    emf = Persistence.createEntityManagerFactory("sales");
  }

  @Override
  public void dispose(EntityManager em) {
    em.close();
    ENTITY_MANAGERS.remove();
  }

  @Override
  public EntityManager provide() {
    final EntityManager em = emf.createEntityManager();
    ENTITY_MANAGERS.set(em);
    closeableService.add(new Closeable() {
      @Override
      public final void close() {
        em.close();
        ENTITY_MANAGERS.remove();
      }
    });
    return em;
  }
}

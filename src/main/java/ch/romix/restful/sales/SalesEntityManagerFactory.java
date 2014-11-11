package ch.romix.restful.sales;

import java.io.Closeable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.glassfish.hk2.api.Factory;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.CloseableService;

public class SalesEntityManagerFactory implements Factory<EntityManager> {
  static final ThreadLocal<EntityManager> ENTITY_MANAGERS = new ThreadLocal<>();
  private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("sales");

  private final CloseableService closeableService;

  @Inject
  public SalesEntityManagerFactory(CloseableService closeableService) {
    this.closeableService = closeableService;
  }

  @Override
  public void dispose(EntityManager em) {
    em.close();
    ENTITY_MANAGERS.remove();
  }

  @Override
  @RequestScoped
  public EntityManager provide() {
    if (ENTITY_MANAGERS.get() == null) {
      createEntityManager();
    }
    return ENTITY_MANAGERS.get();
  }

  private void createEntityManager() {
    final EntityManager em = emf.createEntityManager();
    ENTITY_MANAGERS.set(em);
    closeableService.add(new Closeable() {
      @Override
      public final void close() {
        em.close();
        ENTITY_MANAGERS.remove();
      }
    });
  }
}

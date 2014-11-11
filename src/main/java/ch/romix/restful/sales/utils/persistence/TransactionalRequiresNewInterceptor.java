package ch.romix.restful.sales.utils.persistence;

import javax.persistence.EntityManager;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class TransactionalRequiresNewInterceptor implements MethodInterceptor {

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    Object returnValue;
    EntityManager em = SalesEntityManagerFactory.ENTITY_MANAGERS.get();
    if (em == null) {
      returnValue = invocation.proceed();
    } else {
      em.getTransaction().begin();
      try {
        returnValue = invocation.proceed();
        em.getTransaction().commit();
      } catch (Throwable t) {
        em.getTransaction().rollback();
        throw t;
      }
    }
    return returnValue;
  }
}

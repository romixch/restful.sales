package ch.romix.restful.sales;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.aopalliance.intercept.ConstructorInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.InterceptionService;
import org.glassfish.hk2.utilities.BuilderHelper;

public class TransactionalInterceptionService implements InterceptionService {
  private final static MethodInterceptor REQUIRES_NEW = new TransactionalRequiresNewInterceptor();
  private final static List<MethodInterceptor> REQUIRES_NEW_LIST = Collections
      .singletonList(REQUIRES_NEW);

  @Override
  public Filter getDescriptorFilter() {
    return BuilderHelper.allFilter();
  }

  @Override
  public List<MethodInterceptor> getMethodInterceptors(Method method) {
    if (method.isAnnotationPresent(Transactional.class)) {
      Transactional transactional = method.getAnnotation(Transactional.class);
      switch (transactional.value()) {
        case REQUIRES_NEW:
          return REQUIRES_NEW_LIST;
        case MANDATORY:
          break;
        case NEVER:
          break;
        case REQUIRED:
          break;
        default:
          break;
      }
    }
    return null;
  }

  @Override
  public List<ConstructorInterceptor> getConstructorInterceptors(Constructor<?> constructor) {
    return null;
  }



}

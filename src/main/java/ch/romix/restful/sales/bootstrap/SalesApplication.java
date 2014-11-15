package ch.romix.restful.sales.bootstrap;

import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;

public class SalesApplication extends ResourceConfig {
  @Inject
  public SalesApplication(ServiceLocator serviceLocator) {
    register(new SalesBinder());
    packages("ch.romix.restful.sales");
  }
}

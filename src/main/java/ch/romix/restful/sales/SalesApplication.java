package ch.romix.restful.sales;

import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;

public class SalesApplication extends ResourceConfig {
  @Inject
  public SalesApplication(ServiceLocator serviceLocator) {
    System.out.println("Registering injectables...");
    register(new SalesBinder());
    packages("ch.romix.restful.sales");
  }
}

package ch.romix.restful.sales.bootstrap;

import javax.inject.Singleton;
import javax.persistence.EntityManager;

import org.glassfish.hk2.api.InterceptionService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ch.romix.restful.sales.customer.CustomerService;
import ch.romix.restful.sales.order.OrderService;
import ch.romix.restful.sales.utils.persistence.SalesEntityManagerFactory;
import ch.romix.restful.sales.utils.persistence.TransactionalInterceptionService;

/**
 * Bind all injectables to the hk2 injector
 * 
 * @author roman
 */
public class SalesBinder extends AbstractBinder {

  @Override
  protected void configure() {
    this.bind(TransactionalInterceptionService.class).to(InterceptionService.class).in(Singleton.class);
    this.bindFactory(SalesEntityManagerFactory.class).to(EntityManager.class);
    this.bind(CustomerService.class).to(CustomerService.class);
    this.bind(OrderService.class).to(OrderService.class);
  }

}

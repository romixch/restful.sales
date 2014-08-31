package ch.romix.restful.sales;

import javax.persistence.EntityManager;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ch.romix.restful.sales.logic.SalesService;

public class SalesBinder extends AbstractBinder {

  @Override
  protected void configure() {
    this.bindFactory(SalesEntityManagerFactory.class).to(EntityManager.class);
    this.bind(SalesService.class).to(SalesService.class);
  }

}

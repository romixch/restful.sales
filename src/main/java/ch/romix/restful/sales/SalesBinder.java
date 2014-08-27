package ch.romix.restful.sales;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class SalesBinder extends AbstractBinder {

  @Override
  protected void configure() {
    this.bindFactory(SalesEntityManagerFactory.class);
  }

}

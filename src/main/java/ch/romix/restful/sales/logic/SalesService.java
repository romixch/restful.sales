package ch.romix.restful.sales.logic;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import ch.romix.restful.sales.model.OrderEntity;

public class SalesService {

  @Inject
  private EntityManager em;

  public OrderEntity getOrder(long id) {
    OrderEntity order = em.find(OrderEntity.class, Long.valueOf(id));
    return order;
  }
}

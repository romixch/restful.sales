package ch.romix.restful.sales.logic;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import ch.romix.restful.sales.model.OrderEntity;

public class SalesService {

  @Inject
  private EntityManager em;

  @Transactional(TxType.REQUIRES_NEW)
  public OrderEntity getOrder(long id) {
    OrderEntity order = em.find(OrderEntity.class, Long.valueOf(id));
    return order;
  }

  @Transactional(TxType.REQUIRES_NEW)
  public void saveOrder(OrderEntity order) {
    em.persist(order);
  }

  @Transactional(TxType.REQUIRES_NEW)
  public List<OrderEntity> getAllOrders() {
    return em.createQuery("select o from OrderEntity o", OrderEntity.class).getResultList();
  }
}

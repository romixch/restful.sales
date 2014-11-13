package ch.romix.restful.sales.order;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

public class OrderService {

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

  @Transactional(TxType.REQUIRES_NEW)
  public List<PositionEntity> getPositions(long orderId) {
    TypedQuery<PositionEntity> query =
        em.createQuery("select p from PositionEntity p where p.orderId = {0}", PositionEntity.class);
    query.setParameter(0, orderId);
    return query.getResultList();
  }

  @Transactional(TxType.REQUIRES_NEW)
  public PositionEntity getPosition(long orderId, long positionId) {
    TypedQuery<PositionEntity> query =
        em.createQuery("select p from PositionEntity p where p.Id = {0} and p.orderId = {1}",
            PositionEntity.class);
    query.setParameter(0, orderId);
    query.setParameter(1, positionId);
    return query.getSingleResult();
  }

  @Transactional(TxType.REQUIRES_NEW)
  public void savePosition(PositionEntity pos) {
    em.persist(pos);
  }
}

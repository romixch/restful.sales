package ch.romix.restful.sales.order;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.romix.restful.sales.order.OrderEntity;
import ch.romix.restful.sales.order.PositionEntity;
import ch.romix.restful.sales.order.PositionLink;
import ch.romix.restful.sales.utils.EnhancedMapper;

public class PositionDTOMappingTest {

  @Test
  public void mapEntityToDTOLink() {
    OrderEntity order = new OrderEntity();
    order.setId(1);
    PositionEntity entity = new PositionEntity();
    entity.setId(10);
    entity.setOrder(order);
    entity.setAmount(7);
    entity.setArticleId(42);
    entity.setUnit("ST");
    PositionLink link = EnhancedMapper.map(entity, PositionLink.class);
    assertEquals((Long) entity.getId(), link.getId());
    assertEquals((Long) entity.getOrder().getId(), link.getOrderId());
    assertEquals("/api/orders/1/positions/10", link.getLink());
  }

}

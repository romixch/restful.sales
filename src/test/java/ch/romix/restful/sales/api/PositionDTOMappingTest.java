package ch.romix.restful.sales.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.romix.restful.sales.model.EnhancedMapper;
import ch.romix.restful.sales.model.PositionEntity;

public class PositionDTOMappingTest {

  @Test
  public void mapEntityToDTOLink() {
    PositionEntity entity = new PositionEntity();
    entity.setId(10);
    entity.setOrderId(1);
    entity.setAmount(7);
    entity.setArticleId(42);
    entity.setUnit("ST");
    PositionLink link = EnhancedMapper.map(entity, PositionLink.class);
    assertEquals((Long) entity.getId(), link.getId());
    assertEquals((Long) entity.getOrderId(), link.getOrderId());
    assertEquals("/api/orders/1/positions/10", link.getLink());
  }

}

package ch.romix.restful.sales.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.romix.restful.sales.model.EnhancedMapper;
import ch.romix.restful.sales.model.OrderEntity;

public class OrderDTOMappingTest {

  @Test
  public void mappingFromEntityToDTO() {
    OrderEntity entity = new OrderEntity();
    entity.setCustomerId(1);
    OrderDTO dto = EnhancedMapper.map(entity, OrderDTO.class);
    assertEquals(1, dto.getCustomerId());
  }

}

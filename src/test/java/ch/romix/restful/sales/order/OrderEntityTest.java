package ch.romix.restful.sales.order;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.romix.restful.sales.order.OrderEntity;
import ch.romix.restful.sales.order.PositionEntity;

public class OrderEntityTest {

  @Test
  public void testAddingPositions() {
    OrderEntity order = new OrderEntity();
    order.setCustomerId(22);
    order.addPosition(new PositionEntity());
    assertEquals(1, order.getPositions().size());
  }

}

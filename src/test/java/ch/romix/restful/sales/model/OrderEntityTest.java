package ch.romix.restful.sales.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OrderEntityTest {

  @Test
  public void testAddingPositions() {
    OrderEntity order = new OrderEntity();
    order.setCustomerId(22);
    order.addPosition(new PositionEntity());
    assertEquals(1, order.getPositions().size());
  }

}

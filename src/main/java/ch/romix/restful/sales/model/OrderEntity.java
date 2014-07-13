package ch.romix.restful.sales.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OrderEntity {

  private long id;
  private long customerId;
  private final LocalDateTime created = LocalDateTime.now();
  private final List<PositionEntity> positions = new ArrayList<PositionEntity>();

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setCustomerId(long customerId) {
    this.customerId = customerId;
  }

  public long getCustomerId() {
    return customerId;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public Collection<PositionEntity> getPositions() {
    return positions;
  }

}

package ch.romix.restful.sales.api;

import java.util.Collection;

public class OrderDTO {
  private long id;
  private long customerId;
  private String created;
  private Collection<PositionLink> positions;

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

  public void setCreated(String created) {
    this.created = created;
  }

  public String getCreated() {
    return created;
  }

  public Collection<PositionLink> getPositions() {
    return positions;
  }

  public void setPositions(Collection<PositionLink> positions) {
    this.positions = positions;
  }
}

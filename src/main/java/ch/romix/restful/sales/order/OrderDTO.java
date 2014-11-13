package ch.romix.restful.sales.order;

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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((created == null) ? 0 : created.hashCode());
    result = prime * result + (int) (customerId ^ (customerId >>> 32));
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + ((positions == null) ? 0 : positions.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    OrderDTO other = (OrderDTO) obj;
    if (created == null) {
      if (other.created != null)
        return false;
    } else if (!created.equals(other.created))
      return false;
    if (customerId != other.customerId)
      return false;
    if (id != other.id)
      return false;
    if (positions == null) {
      if (other.positions != null)
        return false;
    } else if (!positions.equals(other.positions))
      return false;
    return true;
  }
}

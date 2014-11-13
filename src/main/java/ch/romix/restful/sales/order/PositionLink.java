package ch.romix.restful.sales.order;

public class PositionLink {
  private Long orderId;
  private Long id;

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  Long getOrderId() {
    return orderId;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public String getLink() {
    return "/api/orders/" + orderId + "/positions/" + id;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
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
    PositionLink other = (PositionLink) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (orderId == null) {
      if (other.orderId != null)
        return false;
    } else if (!orderId.equals(other.orderId))
      return false;
    return true;
  }
}

package ch.romix.restful.sales.api;

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
}

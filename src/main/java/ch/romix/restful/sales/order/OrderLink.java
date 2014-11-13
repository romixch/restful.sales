package ch.romix.restful.sales.order;

public class OrderLink {

  private Long id;


  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public String getLink() {
    return "/api/orders/" + id;
  }
}

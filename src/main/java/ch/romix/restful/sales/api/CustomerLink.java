package ch.romix.restful.sales.api;

public class CustomerLink {
  private long id;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getLink() {
    return "/api/customers/" + id;
  }
}

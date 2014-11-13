package ch.romix.restful.sales.order;

public class PositionDTO {
  private long id;
  private long articleId;
  private long amount;
  private String unit;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getArticleId() {
    return articleId;
  }

  public void setArticleId(long articleId) {
    this.articleId = articleId;
  }

  public long getAmount() {
    return amount;
  }

  public void setAmount(long amount) {
    this.amount = amount;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }
}

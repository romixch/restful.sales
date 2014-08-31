package ch.romix.restful.sales.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class OrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private long customerId;
  private LocalDateTime created;
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
  private List<PositionEntity> positions;

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

  public void addPosition(PositionEntity positionEntity) {
    if (positions == null) {
      positions = new ArrayList<PositionEntity>();
    }
    positions.add(positionEntity);
  }

}

package ch.romix.restful.sales.logic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import ch.romix.restful.sales.model.CustomerEntity;
import ch.romix.restful.sales.model.OrderEntity;
import ch.romix.restful.sales.model.PositionEntity;

public class Data {

  public final static Data INSTANCE = new Data();

  AtomicLong customerSequence = new AtomicLong();
  Map<Long, CustomerEntity> customers = new HashMap<Long, CustomerEntity>();
  AtomicLong orderSequence = new AtomicLong();
  Map<Long, OrderEntity> orders = new HashMap<Long, OrderEntity>();


  private Data() {
    CustomerEntity tim = new CustomerEntity();
    tim.setName("Tim");
    addCustomer(tim);
    CustomerEntity janis = new CustomerEntity();
    janis.setName("Janis");
    addCustomer(janis);

    OrderEntity order = new OrderEntity();
    order.setCustomerId(tim.getId());
    addOrder(order);

    PositionEntity position = new PositionEntity();
    position.setOrderId(order.getId());
    position.setId(10);
    position.setArticleId(42);
    position.setAmount(3);
    position.setUnit("ST");
    addPosition(position);
  }

  public Collection<CustomerEntity> getCustomers() {
    return customers.values();
  }

  public CustomerEntity getCustomer(Long id) {
    return customers.get(id);
  }

  public void addCustomer(CustomerEntity customer) {
    long id = customerSequence.incrementAndGet();
    customer.setId(id);
    customers.put(Long.valueOf(id), customer);
  }

  public Collection<OrderEntity> getOrders() {
    return orders.values();
  }

  public OrderEntity getOrder(Long id) {
    return orders.get(id);
  }

  public void addOrder(OrderEntity order) {
    long id = orderSequence.incrementAndGet();
    order.setId(id);
    orders.put(Long.valueOf(id), order);
  }

  public void addPosition(PositionEntity position) {
    OrderEntity order = orders.get(position.getOrderId());
    order.getPositions().add(position);
  }
}

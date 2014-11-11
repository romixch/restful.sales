package ch.romix.restful.sales.services;

import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import ch.romix.restful.sales.model.CustomerEntity;

public class CustomerService {

  @Inject
  private EntityManager em;

  @Transactional(TxType.REQUIRES_NEW)
  public Collection<CustomerEntity> getCustomers() {
    return em.createQuery("select c from CustomerEntity c", CustomerEntity.class).getResultList();
  }

  @Transactional(TxType.REQUIRES_NEW)
  public CustomerEntity getCustomer(Long id) {
    return em.find(CustomerEntity.class, id);
  }

  @Transactional(TxType.REQUIRES_NEW)
  public CustomerEntity addCustomer(CustomerEntity customer) {
    em.persist(customer);
    return customer;
  }
}

package ch.romix.restful.sales.api;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.hk2.api.Factory;

import ch.romix.restful.sales.logic.Data;
import ch.romix.restful.sales.model.CustomerEntity;
import ch.romix.restful.sales.model.EnhancedMapper;
import ch.romix.restful.sales.model.OrderEntity;

@Path("/customers")
public class CustomerREST {

  @javax.inject.Inject
  private Factory<EntityManager> em;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getCustomers() {
    Collection<CustomerEntity> customers = Data.INSTANCE.getCustomers();
    Collection<CustomerLink> cusList = EnhancedMapper.map(customers, CustomerLink.class);
    return Response.ok(cusList).expires(Date.from(Instant.now().plusSeconds(5))).build();
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getCustomer(@PathParam("id") String id) {
    CustomerEntity customerEntity = Data.INSTANCE.getCustomer(Long.parseLong(id));
    CustomerDTO dto = EnhancedMapper.map(customerEntity, CustomerDTO.class);
    return Response.ok(dto).expires(Date.from(Instant.now().plusSeconds(10))).build();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Response addCustomer(CustomerDTO dto) {
    CustomerEntity customerEntity = EnhancedMapper.map(dto, CustomerEntity.class);
    Data.INSTANCE.addCustomer(customerEntity);
    URI location = URI.create(String.valueOf(customerEntity.getId()));
    return Response.created(location).build();
  }

  @GET
  @Path("{id}/orders")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getOrders(@PathParam("id") String idAsString) {
    final long customerId = Long.parseLong(idAsString);
    Collection<OrderEntity> orders = Data.INSTANCE.getOrders();
    List<OrderLink> customerOrders = new ArrayList<OrderLink>();
    for (OrderEntity orderEntity : orders) {
      if (orderEntity.getCustomerId() == customerId) {
        customerOrders.add(EnhancedMapper.map(orderEntity, OrderLink.class));
      }
    }
    return Response.ok(customerOrders).build();
  }
}

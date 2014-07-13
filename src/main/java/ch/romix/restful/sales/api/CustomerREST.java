package ch.romix.restful.sales.api;

import java.net.URI;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.romix.restful.sales.logic.Data;
import ch.romix.restful.sales.model.CustomerEntity;
import ch.romix.restful.sales.model.EnhancedMapper;

@Path("/customers")
public class CustomerREST {

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
    CustomerEntity customer = Data.INSTANCE.getCustomer(Long.parseLong(id));
    return Response.ok(customer).build();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Response addCustomer(CustomerEntity customer) {
    Data.INSTANCE.addCustomer(customer);
    URI location = URI.create(String.valueOf(customer.getId()));
    return Response.created(location).build();
  }
}

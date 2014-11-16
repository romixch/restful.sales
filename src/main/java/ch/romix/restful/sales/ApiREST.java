package ch.romix.restful.sales;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import ch.romix.restful.sales.customer.CustomerREST;
import ch.romix.restful.sales.order.OrderREST;

@Path("/")
public class ApiREST {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response dictionary(@Context UriInfo uriInfo) {
    return Response.ok(new Dictionary(uriInfo)).expires(Date.from(Instant.now().plus(1, ChronoUnit.HOURS))).build();
  }

  public static final class Dictionary {

    private final UriInfo uriInfo;

    public Dictionary(UriInfo uriInfo) {
      this.uriInfo = uriInfo;
    }

    public String getOrders() {
      return uriInfo.getBaseUriBuilder().path(OrderREST.class).build().toString();
    }

    public String getCustomers() {
      return uriInfo.getBaseUriBuilder().path(CustomerREST.class).build().toString();
    }
  }
}

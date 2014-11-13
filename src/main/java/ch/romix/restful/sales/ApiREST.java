package ch.romix.restful.sales;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class ApiREST {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response dictionary() {
    return Response.ok(new Dictionary())
        .expires(Date.from(Instant.now().plus(1, ChronoUnit.HOURS))).build();
  }

  public static final class Dictionary {
    public String getOrders() {
      return "/api/orders";
    }

    public String getCustomers() {
      return "/api/customers";
    }
  }
}

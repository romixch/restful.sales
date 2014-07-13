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
import javax.ws.rs.core.Response.Status;

import ch.romix.restful.sales.logic.Data;
import ch.romix.restful.sales.model.EnhancedMapper;
import ch.romix.restful.sales.model.OrderEntity;
import ch.romix.restful.sales.model.PositionEntity;

@Path("/orders")
public class OrderREST {
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getOrders() {
    Collection<OrderEntity> orders = Data.INSTANCE.getOrders();
    Collection<OrderLink> orderList = EnhancedMapper.map(orders, OrderLink.class);
    return Response.ok(orderList).expires(Date.from(Instant.now().plusSeconds(5))).build();
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getOrder(@PathParam("id") String id) {
    OrderEntity order = Data.INSTANCE.getOrder(Long.parseLong(id));
    OrderDTO orderDTO = EnhancedMapper.map(order, OrderDTO.class);
    Collection<PositionLink> positions =
        EnhancedMapper.map(order.getPositions(), PositionLink.class);
    orderDTO.setPositions(positions);
    return Response.ok(orderDTO).build();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Response addOrder(OrderDTO orderDTO) {
    OrderEntity orderEntity = EnhancedMapper.map(orderDTO, OrderEntity.class);
    Data.INSTANCE.addOrder(orderEntity);
    URI location = URI.create(String.valueOf(orderEntity.getId()));
    return Response.created(location).build();
  }

  @GET
  @Path("{id}/positions")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPositions(@PathParam("id") String orderId) {
    OrderEntity order = Data.INSTANCE.getOrder(Long.parseLong(orderId));
    Collection<PositionEntity> positions = order.getPositions();
    Collection<PositionLink> positionsLinks = EnhancedMapper.map(positions, PositionLink.class);
    return Response.ok(positionsLinks).build();
  }

  @POST
  @Path("{id}/positions")
  @Produces(MediaType.APPLICATION_JSON)
  public Response addPosition(@PathParam("id") String orderId, PositionDTO position) {
    PositionEntity entity = EnhancedMapper.map(position, PositionEntity.class);
    entity.setOrderId(Long.parseLong(orderId));
    Data.INSTANCE.addPosition(entity);
    URI location = URI.create(String.valueOf(entity.getId()));
    return Response.created(location).build();
  }

  @GET
  @Path("{orderId}/positions/{posId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPosition(@PathParam("orderId") String orderId, @PathParam("posId") String posId) {
    OrderEntity order = Data.INSTANCE.getOrder(Long.parseLong(orderId));
    Collection<PositionEntity> positions = order.getPositions();
    PositionDTO positionDTO = null;
    for (PositionEntity positionEntity : positions) {
      if (positionEntity.getId() == Long.parseLong(posId)) {
        positionDTO = EnhancedMapper.map(positionEntity, PositionDTO.class);
        break;
      }
    }
    if (positionDTO == null) {
      return Response.status(Status.NOT_FOUND).build();
    } else {
      return Response.ok(positionDTO).build();
    }
  }
}

package br.com.kaffeine.shop.api.controllers;


import br.com.kaffeine.shop.api.requests.CreateOrderRequest;
import br.com.kaffeine.shop.infra.ports.inputs.requests.CreateOrderCommand;
import br.com.kaffeine.shop.infra.ports.inputs.usecases.CreateOrderUseCase;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    private final CreateOrderUseCase createOrderUseCase;

    public OrderResource(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    @POST
    public Response createOrder(@Valid CreateOrderRequest request) {
        CreateOrderCommand command =
                new CreateOrderCommand(request.getCustomerId(), request.getTotalAmount());

        var response = createOrderUseCase.execute(command);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    @Path("/{id}")
    public  Response findAllOrders(@PathParam("id") String id) {
        return Response.accepted(createOrderUseCase.findOrder(id)).build();
    }
}
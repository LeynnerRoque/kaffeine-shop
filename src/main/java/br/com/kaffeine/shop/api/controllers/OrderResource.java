package br.com.kaffeine.shop.api.controllers;


import br.com.kaffeine.shop.api.requests.CreateOrderRequest;
import br.com.kaffeine.shop.api.responses.OrderResponse;
import br.com.kaffeine.shop.domains.model.entities.Order;
import br.com.kaffeine.shop.infra.ports.inputs.requests.CreateOrderCommand;
import br.com.kaffeine.shop.infra.ports.inputs.usecases.CreateOrderUseCase;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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

        Order createdOrder = createOrderUseCase.execute(command);

        OrderResponse response = new OrderResponse(createdOrder);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }
}
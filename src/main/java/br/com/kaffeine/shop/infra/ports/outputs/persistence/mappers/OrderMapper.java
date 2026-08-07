package br.com.kaffeine.shop.infra.ports.outputs.persistence.mappers;

import br.com.kaffeine.shop.api.responses.OrderResponse;
import br.com.kaffeine.shop.domains.model.entities.Order;
import br.com.kaffeine.shop.infra.ports.inputs.requests.CreateOrderCommand;
import br.com.kaffeine.shop.infra.ports.outputs.requests.OrderEventPayload;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class OrderMapper {
    public Order map(CreateOrderCommand command) {
        if(command == null) return null;
        return new Order(command.customerId(), command.totalAmount());
    }


    public  OrderResponse map(Order order) {
        return new OrderResponse(order);
    }


    public List<OrderResponse> map(List<Order> orders) {
        return orders.stream().
                filter(Objects::nonNull)
                .map(this::map)
                .toList();
    }

    public OrderEventPayload mapEvent(Order order){
        return new OrderEventPayload(
                order.getId(),
                order.getCustomerId(),
                order.getTotalAmount(),
                order.getStatus().name(),
                order.getCreatedAt()
        );
    }
}

package br.com.kaffeine.shop.application;

import br.com.kaffeine.shop.domains.model.entities.Order;
import br.com.kaffeine.shop.infra.ports.inputs.requests.CreateOrderCommand;


import br.com.kaffeine.shop.infra.ports.inputs.usecases.CreateOrderUseCase;
import br.com.kaffeine.shop.infra.ports.outputs.OrderEventPublisherPort;
import br.com.kaffeine.shop.infra.ports.outputs.OrderRepositoryPort;
import br.com.kaffeine.shop.infra.ports.outputs.persistence.mappers.OrderMapper;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateOrderUseCaseImpl implements CreateOrderUseCase {

    private final OrderRepositoryPort orderRepositoryPort;
    private final OrderEventPublisherPort orderEventPublisherPort;
    private final OrderMapper orderMapper;

    public CreateOrderUseCaseImpl(OrderRepositoryPort orderRepositoryPort,
                                  OrderEventPublisherPort orderEventPublisherPort, OrderMapper orderMapper) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.orderEventPublisherPort = orderEventPublisherPort;
        this.orderMapper = orderMapper;
    }

    @Override
    public Order execute(CreateOrderCommand command) {
        Order savedOrder = orderRepositoryPort.save(orderMapper.map(command));
        orderEventPublisherPort.publishOrderCreated(orderMapper.mapEvent(savedOrder));
        return savedOrder;
    }
}
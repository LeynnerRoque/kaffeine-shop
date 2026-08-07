package br.com.kaffeine.shop.application;

import br.com.kaffeine.shop.domains.model.entities.Order;
import br.com.kaffeine.shop.infra.ports.inputs.requests.CreateOrderCommand;


import br.com.kaffeine.shop.infra.ports.inputs.usecases.CreateOrderUseCase;
import br.com.kaffeine.shop.infra.ports.outputs.OrderEventPublisherPort;
import br.com.kaffeine.shop.infra.ports.outputs.OrderRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped // No Quarkus, dizemos que este bean é gerenciado pelo CDI
public class CreateOrderUseCaseImpl implements CreateOrderUseCase {

    private final OrderRepositoryPort orderRepositoryPort;
    private final OrderEventPublisherPort orderEventPublisherPort;

    // Injeção de dependência via construtor (ótimo para testes unitários fáceis com Mockito)
    public CreateOrderUseCaseImpl(OrderRepositoryPort orderRepositoryPort, 
                                  OrderEventPublisherPort orderEventPublisherPort) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.orderEventPublisherPort = orderEventPublisherPort;
    }

    @Override
    public Order execute(CreateOrderCommand command) {
        // 1. Instancia o Domínio Rico (aqui a validação de negócio e o ID/Status inicial acontecem)
        Order order = new Order(command.customerId(), command.totalAmount());

        // 2. Persiste o pedido no banco de dados através da porta de saída
        Order savedOrder = orderRepositoryPort.save(order);

        // 3. Publica o evento de pedido criado no Kafka através da porta de saída
        orderEventPublisherPort.publishOrderCreated(savedOrder);

        // 4. Retorna o pedido criado
        return savedOrder;
    }
}
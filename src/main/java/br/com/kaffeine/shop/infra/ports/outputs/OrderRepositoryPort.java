package br.com.kaffeine.shop.infra.ports.outputs;

import br.com.kaffeine.shop.domains.model.entities.Order;

import java.util.Optional;

public interface OrderRepositoryPort {
    Order save(Order order);
    Optional<Order> findById(String id);
}
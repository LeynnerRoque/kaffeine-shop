package br.com.kaffeine.shop.infra.ports.inputs.usecases;

import br.com.kaffeine.shop.domains.model.entities.Order;

import java.util.Optional;

public interface FindOrderUseCase {
    Optional<Order> execute(String id);
}

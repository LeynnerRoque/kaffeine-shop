package br.com.kaffeine.shop.infra.ports.inputs.usecases;

import br.com.kaffeine.shop.domains.model.entities.Order;
import br.com.kaffeine.shop.infra.ports.inputs.requests.CreateOrderCommand;

public interface CreateOrderUseCase {
    Order execute(CreateOrderCommand request);
}

package br.com.kaffeine.shop.infra.ports.inputs.usecases;

import br.com.kaffeine.shop.api.responses.OrderResponse;
import br.com.kaffeine.shop.infra.ports.inputs.requests.CreateOrderCommand;

public interface CreateOrderUseCase {
    OrderResponse execute(CreateOrderCommand request);
    OrderResponse findOrder(String id);
}

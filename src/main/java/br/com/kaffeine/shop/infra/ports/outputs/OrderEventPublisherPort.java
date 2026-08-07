package br.com.kaffeine.shop.infra.ports.outputs;

import br.com.kaffeine.shop.infra.ports.outputs.requests.OrderEventPayload;

public interface OrderEventPublisherPort {
    
    void publishOrderCreated(OrderEventPayload order);
}
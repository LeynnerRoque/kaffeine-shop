package br.com.kaffeine.shop.infra.ports.outputs;

import br.com.kaffeine.shop.domains.model.entities.Order;

public interface OrderEventPublisherPort {
    
    void publishOrderCreated(Order order);
}
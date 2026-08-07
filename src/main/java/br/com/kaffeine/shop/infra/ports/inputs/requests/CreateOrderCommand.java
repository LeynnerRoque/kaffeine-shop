package br.com.kaffeine.shop.infra.ports.inputs.requests;

import java.math.BigDecimal;

// Record para encapsular os dados de entrada do caso de uso (Command Pattern)
public record CreateOrderCommand(
        String customerId,
        BigDecimal totalAmount) {
}

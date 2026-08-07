package br.com.kaffeine.shop.infra.ports.inputs.requests;

import java.math.BigDecimal;

public record CreateOrderCommand(
        String customerId,
        BigDecimal totalAmount) {
}

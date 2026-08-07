package br.com.kaffeine.shop.infra.ports.outputs.requests;

import br.com.kaffeine.shop.domains.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderEventPayload(
        String id,
        String customerId,
        BigDecimal totalAmount,
        String status,
        LocalDateTime createdAt
        ) {
}

package br.com.kaffeine.shop.api.responses;

import br.com.kaffeine.shop.domains.model.entities.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderResponse {

    private String id;
    private String customerId;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createdAt;

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.customerId = order.getCustomerId();
        this.totalAmount = order.getTotalAmount();
        this.status = order.getStatus().name();
        this.createdAt = order.getCreatedAt();
    }

    // Getters
    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
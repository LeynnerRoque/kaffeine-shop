package br.com.kaffeine.shop.infra.ports.outputs.persistence.entities;

import br.com.kaffeine.shop.domains.model.entities.Order;
import br.com.kaffeine.shop.domains.model.enums.OrderStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "kaffeine_orders")
public class OrderEntity {

    @Id
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @Column(name = "customer_id", length = 36, nullable = false)
    private String customerId;

    @Column(name = "total_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "status", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Construtor vazio obrigatório pelo JPA
    public OrderEntity() {}

    // Construtor que converte do Domínio para a Entidade JPA
    public OrderEntity(Order order) {
        this.id = order.getId();
        this.customerId = order.getCustomerId();
        this.totalAmount = order.getTotalAmount();
        this.status = order.getStatus();
        this.createdAt = order.getCreatedAt();
        this.updatedAt = order.getUpdatedAt();
    }

    // Método que converte da Entidade JPA de volta para o Domínio Rico
    public Order toDomain() {
        return new Order(
                this.id,
                this.customerId,
                this.totalAmount,
                this.status,
                this.createdAt,
                this.updatedAt
        );
    }
}
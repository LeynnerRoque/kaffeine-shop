package br.com.kaffeine.shop.domains.model.entities;

import br.com.kaffeine.shop.domains.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Order {

    private final String id;
    private final String customerId;
    private final BigDecimal totalAmount;
    private OrderStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Construtor para novos pedidos (gera ID e status inicial PENDING)
    public Order(String customerId, BigDecimal totalAmount) {
        this.id = UUID.randomUUID().toString();
        this.customerId = Objects.requireNonNull(customerId, "Customer ID cannot be null");
        this.totalAmount = Objects.requireNonNull(totalAmount, "Total amount cannot be null");
        
        validateAmount(totalAmount);
        
        this.status = OrderStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    //Conceito de "Modelo de Domínio Anêmico" quando tem apenas getters e setters,
    // a lógica de negócio fica espalhada por serviços)

    // Construtor completo para reidratar a entidade vinda do banco de dados
    public Order(String id, String customerId, BigDecimal totalAmount, OrderStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    //Garantir a consistência do objeto desde o momento do seu nascimento
    private void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Total amount must be greater than zero");
        }
    }

    // Comportamentos de Domínio
    //Representar as mudanças de estado permitidas para o pedido
    // encapsulando as regras de como essa mudança acontece.
    public void complete() {
        this.status = OrderStatus.COMPLETED;
        this.updatedAt = LocalDateTime.now();
    }

    public void fail() {
        this.status = OrderStatus.FAILED;
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
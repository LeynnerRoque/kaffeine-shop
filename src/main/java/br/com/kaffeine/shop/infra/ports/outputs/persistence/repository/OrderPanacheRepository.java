package br.com.kaffeine.shop.infra.ports.outputs.persistence.repository;


import br.com.kaffeine.shop.infra.ports.outputs.persistence.entities.OrderEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderPanacheRepository implements PanacheRepositoryBase<OrderEntity, String> {
    // Métodos como findById, persist, etc., já vêm embutidos aqui graças ao PanacheRepositoryBase
}
package br.com.kaffeine.shop.infra.ports.outputs.persistence.adapters;


import br.com.kaffeine.shop.domains.model.entities.Order;
import br.com.kaffeine.shop.infra.ports.outputs.OrderRepositoryPort;
import br.com.kaffeine.shop.infra.ports.outputs.persistence.entities.OrderEntity;
import br.com.kaffeine.shop.infra.ports.outputs.persistence.repository.OrderPanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.Optional;

@ApplicationScoped
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    private final OrderPanacheRepository panacheRepository;

    public OrderRepositoryAdapter(OrderPanacheRepository panacheRepository) {
        this.panacheRepository = panacheRepository;
    }

    @Override
    @Transactional // Garante que a operação ocorra dentro de uma transação com o banco
    public Order save(Order order) {
        OrderEntity entity = new OrderEntity(order);
        panacheRepository.persist(entity); // Salva ou atualiza no MySQL
        return entity.toDomain();
    }

    @Override
    public Optional<Order> findById(String id) {
        OrderEntity entity = panacheRepository.findById(id);
        return Optional.ofNullable(entity).map(OrderEntity::toDomain);
    }
}
package br.com.kaffeine.shop.infra.messagins;

import br.com.kaffeine.shop.domains.model.entities.Order;
import br.com.kaffeine.shop.infra.ports.outputs.OrderEventPublisherPort;
import br.com.kaffeine.shop.infra.ports.outputs.requests.OrderEventPayload;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class KafkaOrderEventPublisherAdapter implements OrderEventPublisherPort {

    // O Emitter é o canal do MicroProfile Reactive Messaging que envia mensagens para o Kafka
    @Inject
    @Channel("order-created-out") // Nome do canal que configuraremos no application.properties
    Emitter<OrderEventPayload> orderEmitter;

    @Override
    public void publishOrderCreated(Order order) {
        // Mapeia o modelo de domínio para um DTO/Payload de evento leve
        OrderEventPayload payload = new OrderEventPayload(
                order.getId(),
                order.getCustomerId(),
                order.getTotalAmount(),
                order.getStatus().name(),
                order.getCreatedAt()
        );

        // Envia a mensagem para o tópico Kafka (usando o ID do pedido como chave para particionamento)
        orderEmitter.send(KafkaRecord.of(order.getId(), payload));
    }
}
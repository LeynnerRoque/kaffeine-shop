package br.com.kaffeine.shop.infra.messaging;

import br.com.kaffeine.shop.infra.ports.outputs.OrderEventPublisherPort;
import br.com.kaffeine.shop.infra.ports.outputs.persistence.mappers.OrderMapper;
import br.com.kaffeine.shop.infra.ports.outputs.requests.OrderEventPayload;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@Slf4j
public class KafkaOrderEventPublisherAdapter implements OrderEventPublisherPort {

    @Inject
    @Channel("order-created-out")
    Emitter<OrderEventPayload> orderEmitter;

    @Inject
    OrderMapper orderMapper;

    @Override
    public void publishOrderCreated(OrderEventPayload payload) {
        try{
            log.info("Chamada de Envio Kafka...");
            orderEmitter.send(KafkaRecord.of(payload.id(), payload));
            log.info("Envio Kafka com Sucesso {}",payload.id());
        } catch (Exception e) {
            log.info("Erro ao send chamada de Envio Kafka {}",payload.id());
            throw new RuntimeException(e);
        }

    }
}
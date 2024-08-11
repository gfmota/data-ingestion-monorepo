package evolvability.thesis.metadata_service.producer;

import evolvability.thesis.metadata_service.entities.MetadataUpdateMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class MetadataUpdateProducer {
    private static final String METADATA_UPDATE_QUEUE = "metadata-update-queue";

    private final RabbitTemplate rabbitTemplate;

    public void publish(final String collectorId, final Map<String, Object> metadata) {
        log.info("Publishing metadata update for collectorId {}: {}", collectorId, metadata);

        final var message = new MetadataUpdateMessage(collectorId, metadata);
        log.info("Publishing message: {}", message);
        rabbitTemplate.convertAndSend(METADATA_UPDATE_QUEUE, message);
    }
}

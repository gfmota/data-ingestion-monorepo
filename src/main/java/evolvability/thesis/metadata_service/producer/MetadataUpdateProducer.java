package evolvability.thesis.metadata_service.producer;

import evolvability.thesis.metadata_service.entities.MetadataDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MetadataUpdateProducer {
    private static final String METADATA_UPDATE_QUEUE = "metadata-update-queue";

    private final RabbitTemplate rabbitTemplate;

    public void publish(final String collectorId) {
        log.info("Publishing metadata update for collectorId {}", collectorId);

        final var message = new MetadataDTO(collectorId);
        log.info("Publishing message: {}", message);
        rabbitTemplate.convertAndSend(METADATA_UPDATE_QUEUE, message);
    }
}

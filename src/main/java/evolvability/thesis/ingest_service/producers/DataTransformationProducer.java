package evolvability.thesis.ingest_service.producers;

import evolvability.thesis.ingest_service.clients.MetadataClient;
import evolvability.thesis.ingest_service.entities.DataTransformationMessage;
import evolvability.thesis.ingest_service.entities.RawData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataTransformationProducer {
    private static final String DATA_PUBLICATION_QUEUE = "data-transformation-queue";

    private final RabbitTemplate rabbitTemplate;

    private final MetadataClient metadataClient;

    public void publish(final RawData rawData, final Map<String, Object> messageMetadata) {
        log.info("Publishing raw data: {}", rawData);

        Map<String, Object> collectorMetadata;
        try {
            collectorMetadata = metadataClient.getMetadata(rawData.getCollectorId());
        } catch (Exception e) {
            log.error("Error getting metadata for collectorId: {}", rawData.getCollectorId(), e);
            collectorMetadata = Map.of();
        }
        final Map<String, Object> metadata = mergeMetadata(messageMetadata, collectorMetadata);

        final DataTransformationMessage message = new DataTransformationMessage(rawData.getId(), metadata);

        log.info("Publishing message: {}", message);
        rabbitTemplate.convertAndSend(DATA_PUBLICATION_QUEUE, message);
    }

    private Map<String, Object> mergeMetadata(final Map<String, Object> messageMetadata,
                                              final Map<String, Object> collectorMetadata) {
        log.info("Merging metadata: messageMetadata={}, collectorMetadata={}", messageMetadata, collectorMetadata);
        collectorMetadata.forEach((key, value) -> {
            if (!messageMetadata.containsKey(key)) {
                messageMetadata.put(key, value);
            }
        });
        return messageMetadata;
    }
}

package evolvability.thesis.ingest_service.producers;

import evolvability.thesis.clients.metadataservice.MetadataServiceClient;
import evolvability.thesis.common.metadata.Metadata;
import evolvability.thesis.ingest_service.entities.DataTransformationMessage;
import evolvability.thesis.ingest_service.entities.RawData;
import evolvability.thesis.ingest_service.repositories.RawDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataTransformationProducer {
    private static final String DATA_PUBLICATION_QUEUE = "data-transformation-queue";

    private final RabbitTemplate rabbitTemplate;

    private final MetadataServiceClient metadataServiceClient;

    private final RawDataRepository rawDataRepository;

    public void publish(final RawData rawData, final Metadata messageMetadata) {
        log.info("Publishing raw data: {}", rawData);

        Metadata metadata = messageMetadata;
        if (messageMetadata == null) {
            try {
                metadata = metadataServiceClient.getMetadata(rawData.getCollectorId());
            } catch (Exception e) {
                log.error("Error getting metadata for collectorId: {}", rawData.getCollectorId(), e);
                log.info("No metadata for collectorId {}, dropping publication", rawData.getCollectorId(), e);
                return;
            }
        }

        final DataTransformationMessage message = new DataTransformationMessage(rawData.getData(), metadata);

        log.info("Publishing message: {}", message);
        rabbitTemplate.convertAndSend(DATA_PUBLICATION_QUEUE, message);

        rawData.setProcessed(true);
        rawDataRepository.save(rawData);
    }
}

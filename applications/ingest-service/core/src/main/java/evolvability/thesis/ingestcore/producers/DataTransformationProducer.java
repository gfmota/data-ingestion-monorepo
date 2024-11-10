package evolvability.thesis.ingestcore.producers;

import evolvability.thesis.clients.metadataservice.MetadataServiceClient;
import evolvability.thesis.common.metadata.Metadata;
import evolvability.thesis.ingestcore.entities.DataTransformationMessage;
import evolvability.thesis.ingestcore.entities.RawData;
import evolvability.thesis.ingestcore.exceptions.DatabaseOperationFailedException;
import evolvability.thesis.ingestcore.services.RawDataService;
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

    private final RawDataService rawDataService;

    public void publish(final RawData rawData, final Metadata messageMetadata) throws DatabaseOperationFailedException {
        log.info("Publishing raw data: {}", rawData);

        Metadata metadata = messageMetadata;
        if (messageMetadata == null) {
            try {
                metadata = metadataServiceClient.getMetadata(rawData.getSourceId());
            } catch (Exception e) {
                log.error("Error getting metadata for sourceId: {}", rawData.getSourceId(), e);
                log.info("No metadata for sourceId {}, dropping publication", rawData.getSourceId(), e);
                return;
            }
        }

        final DataTransformationMessage message = new DataTransformationMessage(rawData.getData(), metadata);

        log.info("Publishing message: {}", message);
        rabbitTemplate.convertAndSend(DATA_PUBLICATION_QUEUE, message);

        rawData.setProcessed(true);
        rawDataService.update(rawData);
    }
}

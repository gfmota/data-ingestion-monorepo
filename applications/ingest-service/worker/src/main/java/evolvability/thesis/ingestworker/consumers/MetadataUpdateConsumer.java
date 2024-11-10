package evolvability.thesis.ingestworker.consumers;

import evolvability.thesis.common.metadata.MetadataDTO;
import evolvability.thesis.ingestcore.entities.RawData;
import evolvability.thesis.ingestcore.exceptions.DatabaseOperationFailedException;
import evolvability.thesis.ingestcore.producers.DataTransformationProducer;
import evolvability.thesis.ingestcore.services.RawDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MetadataUpdateConsumer {
    private static final String METADATA_UPDATE_QUEUE = "metadata-update-queue";

    private final RawDataService rawDataService;

    private final DataTransformationProducer dataTransformationProducer;

    @RabbitListener(queues = METADATA_UPDATE_QUEUE)
    private void consume(final MetadataDTO message) throws DatabaseOperationFailedException {
        log.info("Received update for metadata: {}", message);

        final List<RawData> collectorsRawData = rawDataService.findNonProcessedRawDataInTimerangeByCollectorId(
                message.collectorId(), message.startDate() , message.expirationDate());
        log.info("Found {} non processed raw data entries for collectorId: {} between {} and {}",
                collectorsRawData.size(), message.collectorId(), message.startDate(), message.expirationDate());

        for (RawData rawData : collectorsRawData) {
            dataTransformationProducer.publish(rawData, message.metadata());
        }
    }
}

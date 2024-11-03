package evolvability.thesis.ingest_service.consumers;

import evolvability.thesis.common.metadata.MetadataDTO;
import evolvability.thesis.ingest_service.entities.RawData;
import evolvability.thesis.ingest_service.producers.DataTransformationProducer;
import evolvability.thesis.ingest_service.repositories.RawDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MetadataUpdateConsumer {
    private static final String METADATA_UPDATE_QUEUE = "metadata-update-queue";

    private static final LocalDateTime MIN_DATE = LocalDateTime.of(0, 1, 1, 0, 0, 0);

    private static final LocalDateTime MAX_DATE = LocalDateTime.of(9999, 12, 31, 23, 59, 59);

    private final RawDataRepository rawDataRepository;

    private final DataTransformationProducer dataTransformationProducer;

    @RabbitListener(queues = METADATA_UPDATE_QUEUE)
    private void consume(final MetadataDTO message) {
        log.info("Received update for metadata: {}", message);

        final String collectorId = message.collectorId();
        final LocalDateTime startDate = message.startDate() == null ? MIN_DATE : message.startDate();
        final LocalDateTime expirationDate = message.expirationDate() == null ? MAX_DATE  : message.expirationDate();
        final List<RawData> collectorsRawData = rawDataRepository.findByCollectorIdAndCollectedAtBetweenAndProcessedFalse(
                collectorId, startDate , expirationDate);
        log.info("Found {} raw data non processed entries for collectorId: {} between {} and {}",
                collectorsRawData.size(), collectorId, startDate, expirationDate);

        collectorsRawData.forEach(rawData -> dataTransformationProducer.publish(rawData, message.metadata()));
    }
}

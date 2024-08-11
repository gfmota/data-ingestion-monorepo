package evolvability.thesis.ingest_service.consumers;

import evolvability.thesis.ingest_service.entities.MetadataUpdateMessage;
import evolvability.thesis.ingest_service.entities.RawData;
import evolvability.thesis.ingest_service.producers.DataTransformationProducer;
import evolvability.thesis.ingest_service.repositories.RawDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class MetadataUpdateConsumer {
    private static final String METADATA_UPDATE_QUEUE = "metadata-update-queue";

    private final RawDataRepository rawDataRepository;

    private final DataTransformationProducer dataTransformationProducer;

    @RabbitListener(queues = METADATA_UPDATE_QUEUE)
    private void consume(final MetadataUpdateMessage message) {
        log.info("Received update for metadata: {}", message);

        final List<RawData> collectorsRawData = rawDataRepository.findByCollectorId(message.collectorId());
        log.info("Found {} raw data entries for collectorId: {}", collectorsRawData.size(), message.collectorId());

        collectorsRawData.forEach(rawData -> {
            dataTransformationProducer.publish(rawData, Map.of());
        });
    }
}

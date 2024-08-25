package evolvability.thesis.ingest_service.consumers;

import evolvability.thesis.ingest_service.entities.IngestedData;
import evolvability.thesis.ingest_service.entities.RawData;
import evolvability.thesis.ingest_service.producers.DataTransformationProducer;
import evolvability.thesis.ingest_service.repositories.RawDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataIngestionConsumer {
    private static final String DATA_INGESTION_QUEUE = "data-ingestion-queue";

    private final RawDataRepository rawDataRepository;

    private final DataTransformationProducer dataTransformationProducer;

    @RabbitListener(queues = DATA_INGESTION_QUEUE)
    private void ingestData(final IngestedData data) {
        log.info("Ingesting data: {}", data);
        final RawData rawData = mapRawDataFromIngestedData(data);
        log.info("Mapped data: {}", rawData);

        final RawData rawDataResult = rawDataRepository.insert(rawData);
        log.info("Raw data result: {}", rawDataResult);
        dataTransformationProducer.publish(rawDataResult, data.metadata());
    }

    private RawData mapRawDataFromIngestedData(final IngestedData ingestedData) {
        return RawData.builder()
                .collectedAt(ingestedData.header().collectedAt())
                .collectorId(ingestedData.header().source())
                .data(ingestedData.data())
                .processed(false)
                .build();
    }
}

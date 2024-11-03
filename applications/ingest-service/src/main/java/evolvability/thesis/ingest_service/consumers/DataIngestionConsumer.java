package evolvability.thesis.ingest_service.consumers;

import evolvability.thesis.common.dataingestionqueue.DataIngestionMessage;
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
    private void ingestData(final DataIngestionMessage message) {
        log.info("Ingesting data: {}", message);
        final RawData rawData = mapRawDataFromIngestedData(message);
        log.info("Mapped data: {}", rawData);

        final RawData rawDataResult = rawDataRepository.insert(rawData);
        log.info("Raw data result: {}", rawDataResult);
        dataTransformationProducer.publish(rawDataResult, message.metadata());
    }

    private RawData mapRawDataFromIngestedData(final DataIngestionMessage ingestedData) {
        return RawData.builder()
                .collectedAt(ingestedData.header().collectedAt())
                .collectorId(ingestedData.header().source())
                .dataId(ingestedData.header().dataId())
                .data(ingestedData.data())
                .processed(false)
                .build();
    }
}

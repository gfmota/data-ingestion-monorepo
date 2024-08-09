package evolvability.thesis.ingest_service.consumers;

import evolvability.thesis.ingest_service.entities.IngestedData;
import evolvability.thesis.ingest_service.entities.RawData;
import evolvability.thesis.ingest_service.repositories.RawDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataIngestionConsumer {
    private static final String DATA_INGESTION_QUEUE = "data-ingestion-queue";

    private final RawDataRepository rawDataRepository;

    @RabbitListener(queues = DATA_INGESTION_QUEUE)
    public void ingestData(final IngestedData data) {
        System.out.println("Received data: " + data);
        final RawData rawData = mapRawDataFromIngestedData(data);
        System.out.println("Mapped data: " + rawData);

        rawDataRepository.insert(rawData);
    }

    private RawData mapRawDataFromIngestedData(final IngestedData ingestedData) {
        return RawData.builder()
                .collectedAt(ingestedData.collectedAt())
                .collectorId(ingestedData.collectorId())
                .data(ingestedData.data())
                .processed(false)
                .build();
    }
}

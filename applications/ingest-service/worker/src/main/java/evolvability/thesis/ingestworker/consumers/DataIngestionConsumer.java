package evolvability.thesis.ingestworker.consumers;

import evolvability.thesis.common.dataingestionqueue.DataIngestionMessage;
import evolvability.thesis.ingestcore.entities.RawData;
import evolvability.thesis.ingestcore.exceptions.DatabaseOperationFailedException;
import evolvability.thesis.ingestcore.producers.DataTransformationProducer;
import evolvability.thesis.ingestcore.services.RawDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataIngestionConsumer {
    private static final String DATA_INGESTION_QUEUE = "data-ingestion-queue";

    private final RawDataService rawDataService;

    private final DataTransformationProducer dataTransformationProducer;

    @RabbitListener(queues = DATA_INGESTION_QUEUE)
    private void ingestData(final DataIngestionMessage message) throws DatabaseOperationFailedException {
        log.info("Ingesting data: {}", message);

        final RawData rawData = rawDataService.insert(
                message.header().source(),
                message.header().collectedAt(),
                message.header().dataId(),
                message.data());
        log.info("Raw data {} inserted", rawData.getId());

        dataTransformationProducer.publish(rawData, message.metadata());
    }
}

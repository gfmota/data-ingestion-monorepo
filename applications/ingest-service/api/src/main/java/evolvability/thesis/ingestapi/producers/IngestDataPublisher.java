package evolvability.thesis.ingestapi.producers;

import evolvability.thesis.common.dataingestionqueue.DataIngestionMessage;
import evolvability.thesis.common.dataingestionqueue.Header;
import evolvability.thesis.common.metadata.Metadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class IngestDataPublisher {
    private static final String DATA_INGESTION_QUEUE = "data-ingestion-queue";

    private final RabbitTemplate rabbitTemplate;

    public void publish(final String collectorId,
                        final String dataId,
                        final LocalDateTime collectedAt,
                        final Object data,
                        final Metadata metadata) {
        log.info("Publishing data for ingestion: collectorId={}, dataId={}, collectedAt={}, data={}, metadata={}",
                collectorId, dataId, collectedAt, data, metadata);

        final Header messageHeader = new Header(collectedAt, collectorId, dataId);
        final DataIngestionMessage message = new DataIngestionMessage(messageHeader, metadata, data);

        log.info("Publishing message: {}", message);
        rabbitTemplate.convertAndSend(DATA_INGESTION_QUEUE, message);
    }
}

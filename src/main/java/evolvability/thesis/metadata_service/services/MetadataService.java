package evolvability.thesis.metadata_service.services;

import evolvability.thesis.metadata_service.entities.Metadata;
import evolvability.thesis.metadata_service.producer.MetadataUpdateProducer;
import evolvability.thesis.metadata_service.repositories.MetadataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetadataService {
    private final MetadataRepository metadataRepository;

    private final MetadataUpdateProducer metadataUpdateProducer;

    public String createMetadata(final String collectorId, final Map<String, Object> metadata) {
        final var metadataObject = Metadata.builder()
                .collectorId(collectorId)
                .metadata(metadata)
                .receivedAt(LocalDateTime.now())
                .build();
        final var savedObject = metadataRepository.save(metadataObject);

        metadataUpdateProducer.publish(collectorId);
        return savedObject.getId();
    }

    public Map<String, Object> getMetadata(final String collectorId) {
        return metadataRepository.findFirstByCollectorIdOrderByReceivedAtDesc(collectorId)
                .map(metadata -> {
                    log.info("Most recent metadata found: {}", metadata);
                    return metadata.getMetadata();
                })
                .orElse(Map.of());
    }
}

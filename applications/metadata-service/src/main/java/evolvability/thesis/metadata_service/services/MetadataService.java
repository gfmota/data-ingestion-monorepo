package evolvability.thesis.metadata_service.services;

import evolvability.thesis.common.metadata.Metadata;
import evolvability.thesis.metadata_service.entities.MetadataEntity;
import evolvability.thesis.common.metadata.MetadataDTO;
import evolvability.thesis.metadata_service.exceptions.MetadataNotFoundException;
import evolvability.thesis.metadata_service.producer.MetadataUpdateProducer;
import evolvability.thesis.metadata_service.repositories.MetadataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetadataService {
    private final MetadataRepository metadataRepository;

    private final MetadataUpdateProducer metadataUpdateProducer;

    public String createMetadata(final MetadataDTO metadata) {
        final var metadataObject = MetadataEntity.builder()
                .collectorId(metadata.collectorId())
                .metadata(metadata.metadata())
                .startDate(metadata.startDate())
                .expirationDate(metadata.expirationDate())
                .receivedAt(LocalDateTime.now())
                .build();
        final var savedObject = metadataRepository.save(metadataObject);

        metadataUpdateProducer.publish(metadata);
        return savedObject.getId();
    }

    public Metadata getMetadata(final String collectorId) throws MetadataNotFoundException {
        return metadataRepository.findMostRecentValidByCollectorId(collectorId, LocalDateTime.now())
                .map(metadataEntity -> {
                    log.info("Most recent metadata found: {}", metadataEntity);
                    return metadataEntity.getMetadata();
                })
                .orElseThrow(() -> new MetadataNotFoundException("Valid metadata not found"));
    }
}

package evolvability.thesis.ingestcore.services;

import evolvability.thesis.ingestcore.entities.RawData;
import evolvability.thesis.ingestcore.exceptions.DatabaseOperationFailedException;
import evolvability.thesis.ingestcore.repositories.RawDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RawDataService {
    private static final LocalDateTime MIN_DATE = LocalDateTime.of(0, 1, 1, 0, 0, 0);

    private static final LocalDateTime MAX_DATE = LocalDateTime.of(9999, 12, 31, 23, 59, 59);

    private final RawDataRepository rawDataRepository;

    public void update(final RawData rawData) throws DatabaseOperationFailedException {
        log.info("Updating raw data {} with values: {}", rawData.getId(), rawData);

        try {
            rawDataRepository.save(rawData);
        } catch (Exception e) {
            log.error("Error updating raw data {}", rawData, e);
            throw new DatabaseOperationFailedException("Error updating raw data", e);
        }
    }

    public RawData insert(final String collectorId,
                          final LocalDateTime collectedAt,
                          final String dataId,
                          final Object data) throws DatabaseOperationFailedException {
        final RawData rawData = RawData.builder()
                .collectorId(collectorId)
                .collectedAt(collectedAt)
                .dataId(dataId)
                .data(data)
                .processed(false)
                .build();

        log.info("Inserting raw data {}", rawData);
        try {
            return rawDataRepository.save(rawData);
        } catch (Exception e) {
            log.error("Error inserting raw data {}", rawData, e);
            throw new DatabaseOperationFailedException("Error inserting raw data", e);
        }
    }

    public List<RawData> findNonProcessedRawDataInTimerangeByCollectorId(final String collectorId,
                                                                         final LocalDateTime from,
                                                                         final LocalDateTime to) {
        log.info("Finding non processed raw data for collectorId: {} between {} and {}", collectorId, from, to);
        final LocalDateTime startDate = from == null ? MIN_DATE : from;
        final LocalDateTime expirationDate = to == null ? MAX_DATE  : to;
        try {
            return rawDataRepository.findByCollectorIdAndCollectedAtBetweenAndProcessedFalse(
                    collectorId, startDate , expirationDate);
        } catch (Exception e) {
            log.error("Error finding non processed raw data for collectorId: {} between {} and {}, returning empty list",
                    collectorId, startDate, expirationDate, e);
            return List.of();
        }
    }
}
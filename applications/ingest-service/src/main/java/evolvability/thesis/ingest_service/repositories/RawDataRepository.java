package evolvability.thesis.ingest_service.repositories;

import evolvability.thesis.ingest_service.entities.RawData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RawDataRepository extends MongoRepository<RawData, String>{
    List<RawData> findByCollectorIdAndCollectedAtBetweenAndProcessedFalse(String collectorId, LocalDateTime startDate, LocalDateTime endDate);
}

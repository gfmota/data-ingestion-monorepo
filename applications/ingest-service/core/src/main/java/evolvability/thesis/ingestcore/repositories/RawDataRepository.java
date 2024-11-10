package evolvability.thesis.ingestcore.repositories;

import evolvability.thesis.ingestcore.entities.RawData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RawDataRepository extends MongoRepository<RawData, String>{
    List<RawData> findBysourceIdAndCollectedAtBetweenAndProcessedFalse(String sourceId, LocalDateTime startDate, LocalDateTime endDate);
}

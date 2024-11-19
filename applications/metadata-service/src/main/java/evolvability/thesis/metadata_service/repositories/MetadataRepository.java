package evolvability.thesis.metadata_service.repositories;

import evolvability.thesis.metadata_service.entities.MetadataEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MetadataRepository extends MongoRepository<MetadataEntity, String> {
    @Query(value = "{ '$and': [ " +
            "{ 'sourceId': ?0 }, " + // Filter by sourceId
            "{ '$or': [ " +
            "{ 'startDate': null, 'expirationDate': { '$gte': ?1 } }, " + // If startDate is null, valid if expirationDate is in the future
            "{ 'startDate': { '$lt': ?1 }, 'expirationDate': { '$gte': ?1 } }, " + // Valid if current date is between startDate and expirationDate
            "{ 'startDate': { '$lte': ?1 }, 'expirationDate': null } " + // Valid if expirationDate is null and startDate is in the past or current
            "] } ] }",
            sort = "{ 'receivedAt': -1 }")
    List<MetadataEntity> findFirstByValidDateAndSourceId(String sourceId, LocalDateTime now);
}

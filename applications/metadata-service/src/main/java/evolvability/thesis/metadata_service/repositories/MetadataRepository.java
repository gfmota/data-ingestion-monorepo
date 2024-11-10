package evolvability.thesis.metadata_service.repositories;

import evolvability.thesis.metadata_service.entities.MetadataEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MetadataRepository extends MongoRepository<MetadataEntity, String> {
    @Query("{ '$and': [ " +
            "{ 'sourceId': ?1 }, " + // Filter by sourceId
            "{ '$or': [ " +
            "{ 'startDate': null, 'expirationDate': { '$gte': ?0 } }, " + // If startDate is null, valid if expirationDate is in the future
            "{ 'startDate': { '$lt': ?0 }, 'expirationDate': { '$gte': ?0 } }, " + // Valid if current date is between startDate and expirationDate
            "{ 'startDate': { '$lte': ?0 }, 'expirationDate': null } " + // Valid if expirationDate is null and startDate is in the past or current
            "] } ] }")
    Optional<MetadataEntity> findMostRecentValidBySourceId(String sourceId, LocalDateTime now);
}

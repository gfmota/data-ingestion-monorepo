package evolvability.thesis.metadata_service.repositories;

import evolvability.thesis.metadata_service.entities.Metadata;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetadataRepository extends MongoRepository<Metadata, String> {
}

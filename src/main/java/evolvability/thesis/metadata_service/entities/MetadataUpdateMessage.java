package evolvability.thesis.metadata_service.entities;

import java.io.Serializable;
import java.util.Map;

public record MetadataUpdateMessage(String collectorId, Map<String, Object> metadata) implements Serializable {
}

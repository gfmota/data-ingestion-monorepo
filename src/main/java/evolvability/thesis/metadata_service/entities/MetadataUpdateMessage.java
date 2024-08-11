package evolvability.thesis.metadata_service.entities;

import java.io.Serializable;

public record MetadataUpdateMessage(String collectorId) implements Serializable {
}

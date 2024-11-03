package evolvability.thesis.metadata_service.entities;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

public record MetadataDTO(
        @NotNull String collectorId,
        LocalDateTime startDate,
        LocalDateTime expirationDate,
        @NotNull Map<String, Object> metadata
) implements Serializable {
}

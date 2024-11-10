package evolvability.thesis.common.metadata;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

public record MetadataDTO(
        @NotNull(message = "collectorId cannot be null") String collectorId,
        LocalDateTime startDate,
        LocalDateTime expirationDate,
        @NotNull(message = "metadata cannot be null") Metadata metadata
) implements Serializable {
}

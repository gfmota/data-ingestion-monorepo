package evolvability.thesis.common.metadata;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

public record MetadataDTO(
        @NotNull String collectorId,
        LocalDateTime startDate,
        LocalDateTime expirationDate,
        @NotNull Metadata metadata
) implements Serializable {
}

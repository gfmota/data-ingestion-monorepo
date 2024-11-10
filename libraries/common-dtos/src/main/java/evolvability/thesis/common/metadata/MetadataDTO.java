package evolvability.thesis.common.metadata;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

public record MetadataDTO(
        @NotNull(message = "sourceId cannot be null") String sourceId,
        LocalDateTime startDate,
        LocalDateTime expirationDate,
        @NotNull(message = "metadata cannot be null") Metadata metadata
) implements Serializable {
}

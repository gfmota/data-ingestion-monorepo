package evolvability.thesis.ingestapi.dtos;

import evolvability.thesis.common.metadata.Metadata;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngestDataRequestBodyDTO {
    @NotNull(message = "Data cannot be null")
    private Object data;

    @NotNull(message = "Collected at cannot be null")
    private LocalDateTime collectedAt;

    private Metadata metadata;
}

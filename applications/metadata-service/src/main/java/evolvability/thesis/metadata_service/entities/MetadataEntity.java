package evolvability.thesis.metadata_service.entities;

import evolvability.thesis.common.metadata.Metadata;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "metadata")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetadataEntity {
    @Id
    private String id;

    @Indexed
    private String sourceId;

    @Indexed
    private LocalDateTime receivedAt;

    private LocalDateTime startDate;

    private LocalDateTime expirationDate;

    private Metadata metadata;
}

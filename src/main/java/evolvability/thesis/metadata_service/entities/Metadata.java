package evolvability.thesis.metadata_service.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "metadata")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Metadata {
    @Id
    private String id;

    @Indexed
    private String collectorId;

    @Indexed
    private LocalDateTime receivedAt;

    private Map<String, Object> metadata;
}

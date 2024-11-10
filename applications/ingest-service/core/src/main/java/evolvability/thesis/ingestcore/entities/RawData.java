package evolvability.thesis.ingestcore.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "raw_data")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RawData {
    @Id
    private String id;

    @Indexed
    private String sourceId;

    @Indexed
    private LocalDateTime collectedAt;

    private Boolean processed;

    private Object data;
}

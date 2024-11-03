package evolvability.thesis.austrian_geosphere_data_collector.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public record Header(LocalDateTime collectedAt, String source, String dataId) implements Serializable {
}

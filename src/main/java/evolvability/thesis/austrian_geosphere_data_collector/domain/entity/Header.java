package evolvability.thesis.austrian_geosphere_data_collector.domain.entity;

import java.time.LocalDateTime;

public record Header(LocalDateTime collectedAt, String source) {
}

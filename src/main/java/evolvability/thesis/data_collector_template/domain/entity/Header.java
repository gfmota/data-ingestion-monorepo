package evolvability.thesis.data_collector_template.domain.entity;

import java.time.LocalDateTime;

public record Header(LocalDateTime collectedAt, String source) {
}

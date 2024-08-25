package evolvability.thesis.ingest_service.entities;

import java.time.LocalDateTime;

public record Header(LocalDateTime collectedAt, String source, String dataType) {
}


package evolvability.thesis.ingest_service.entities;

import java.time.LocalDateTime;

public record IngestedData(
        String collectorId,
        LocalDateTime collectedAt,
        Object data,
        Object metadata
) {}

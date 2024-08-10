package evolvability.thesis.ingest_service.entities;

import java.time.LocalDateTime;
import java.util.Map;

public record IngestedData(
        String collectorId,
        LocalDateTime collectedAt,
        Object data,
        Map<String, Object> metadata
) {}

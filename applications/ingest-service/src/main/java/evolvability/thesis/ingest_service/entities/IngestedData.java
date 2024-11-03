package evolvability.thesis.ingest_service.entities;

import java.util.Map;

public record IngestedData(
        Header header,
        Object data,
        Map<String, Object> metadata
) {}

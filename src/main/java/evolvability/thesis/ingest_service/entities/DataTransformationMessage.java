package evolvability.thesis.ingest_service.entities;

import java.util.Map;

public record DataTransformationMessage(String rawDataId, Map<String, Object> metadata) {}

package evolvability.thesis.ingest_service.entities;

import java.util.Map;

public record DataTransformationMessage(Object rawData, Map<String, Object> metadata) {}

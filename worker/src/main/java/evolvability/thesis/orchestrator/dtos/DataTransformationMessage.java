package evolvability.thesis.orchestrator.dtos;

import java.util.Map;

public record DataTransformationMessage(Object rawData, Map<String, Object> metadata) {}

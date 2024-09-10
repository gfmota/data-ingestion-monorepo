package evolvability.thesis.orchestrator.infrastructure.consumers;

import java.util.Map;

public record DataTransformationMessage(String rawDataId, Map<String, Object> metadata) {}

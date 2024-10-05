package evolvability.thesis.orchestrator.consumers;

import java.util.Map;

public record DataTransformationMessage(String rawDataId, Map<String, Object> metadata) {}

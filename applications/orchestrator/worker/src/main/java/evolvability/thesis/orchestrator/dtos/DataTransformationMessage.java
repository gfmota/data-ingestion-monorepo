package evolvability.thesis.orchestrator.dtos;

import com.fasterxml.jackson.databind.JsonNode;
import evolvability.thesis.common.metadata.Metadata;

public record DataTransformationMessage(JsonNode rawData, Metadata metadata) {}

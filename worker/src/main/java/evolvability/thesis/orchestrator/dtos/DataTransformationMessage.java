package evolvability.thesis.orchestrator.dtos;

import com.fasterxml.jackson.databind.JsonNode;

public record DataTransformationMessage(JsonNode rawData, Metadata metadata) {}

package evolvability.thesis.common.datatransformationqueue;

import com.fasterxml.jackson.databind.JsonNode;
import evolvability.thesis.common.metadata.Metadata;

public record DataTransformationMessage(JsonNode rawData, Metadata metadata) {
}

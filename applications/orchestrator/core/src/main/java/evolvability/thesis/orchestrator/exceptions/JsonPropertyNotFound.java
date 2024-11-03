package evolvability.thesis.orchestrator.exceptions;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonPropertyNotFound extends Exception {
    public JsonPropertyNotFound(final String property, final JsonNode json) {
            super(String.format("Json property %s not found in %s", property, json.asText()));
    }
}

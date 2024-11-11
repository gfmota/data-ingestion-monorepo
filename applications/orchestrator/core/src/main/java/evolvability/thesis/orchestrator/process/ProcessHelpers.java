package evolvability.thesis.orchestrator.process;

import com.fasterxml.jackson.databind.JsonNode;
import evolvability.thesis.orchestrator.exceptions.JsonPropertyNotFound;
import evolvability.thesis.orchestrator.exceptions.TransformerNotFound;
import evolvability.thesis.orchestrator.transformers.Transformer;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isNumeric;

@Slf4j
public class ProcessHelpers {
    public static JsonNode getJsonProperty(final JsonNode json, final String path) throws JsonPropertyNotFound {
        log.info("Getting property {} in {}", path, json);
        String[] properties = path.split("\\.");
        JsonNode currentNode = json;

        for (String property : properties) {
            if (currentNode != null && isNumeric(property) && currentNode.isArray()) {
                currentNode = currentNode.get(Integer.parseInt(property));
                continue;
            }

            if (currentNode != null && currentNode.has(property)) {
                currentNode = currentNode.get(property);
                continue;
            }

            throw new JsonPropertyNotFound(property, json);
        }
        return currentNode;
    }

    public static Long getUnixTimestamp(final String timestamp) {
        ZonedDateTime zdt = ZonedDateTime.parse(timestamp, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return zdt.toEpochSecond();
    }

    public static Double getValue(final Double value, final String unit, final String targetUnit)
            throws TransformerNotFound {
        if (Objects.equals(unit, targetUnit)) {
            return value;
        }
        log.info("Value: {} Unit: {} TargetUnit: {}", value, unit, targetUnit);

        final Reflections reflections = new Reflections("evolvability.thesis.orchestrator.transformers");
        Set<Class<? extends Transformer>> transformers = reflections.getSubTypesOf(Transformer.class);

        for (final Class<? extends Transformer> transformer : transformers) {
            Transformer instance = null;
            try {
                instance = transformer.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                log.error("Error creating transformer instance", e);
                continue;
            }
            if (Objects.equals(instance.getInputUnit(), unit)) {
                try {
                    return getValue(instance.transform(value), instance.getOutputUnit(), targetUnit);
                } catch (TransformerNotFound ignored) {
                }
            }
        }
        throw new TransformerNotFound(unit, targetUnit);
    }
}

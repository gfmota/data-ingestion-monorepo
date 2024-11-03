package evolvability.thesis.orchestrator.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import evolvability.thesis.common.metadata.Metadata;
import evolvability.thesis.orchestrator.dtos.ResultDTO;
import evolvability.thesis.orchestrator.dtos.result.BranchDTO;
import evolvability.thesis.orchestrator.dtos.result.DataDTO;
import evolvability.thesis.orchestrator.dtos.result.DataTypeDTO;
import evolvability.thesis.orchestrator.dtos.result.StationDTO;
import evolvability.thesis.orchestrator.exceptions.JsonPropertyNotFound;
import evolvability.thesis.orchestrator.exceptions.TransformerNotFound;
import evolvability.thesis.orchestrator.transformers.Transformer;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isNumeric;

@Service
@Slf4j
public class ProcessService {

    public void processMessage(final JsonNode data, final Metadata metadata) throws JsonPropertyNotFound {
        final ResultDTO result = new ResultDTO();

        // Extract datatypes
        metadata.datatypes().forEach(dataType -> {
            result.getDataTypes().add(DataTypeDTO.builder()
                    .name(dataType.datatypeName())
                    .unit(dataType.targetUnit())
                    .rtype(dataType.rtype())
                    .build());
        });

        // Extract stations
        result.setStations(getStations(data, metadata));

        // Extract measurements
        result.setMeasurements(getMeasurements(data, metadata));

        printJson(result);
    }

    private BranchDTO getMeasurements(final JsonNode data, final Metadata metadata) throws JsonPropertyNotFound {
        final Metadata.Results resultsMetadata = metadata.results();
        if (resultsMetadata.type().isArray()) {
            final ArrayNode results = (ArrayNode) getJsonProperty(data, resultsMetadata.property());
            final BranchDTO measurements = new BranchDTO();
            for (JsonNode result : results) {
                final String stationId = getJsonProperty(result, resultsMetadata.idProperty()).asText();
                final BranchDTO resultsBranch = new BranchDTO();

                for (Metadata.Datatype datatype : metadata.datatypes()) {
                    Double rawValue = null;
                    try {
                        final JsonNode rawValueJson = getJsonProperty(result, datatype.valueProperty());
                        if (rawValueJson.isArray()) {
                            rawValue = rawValueJson.get(0).asDouble();
                        } else {
                            rawValue = rawValueJson.asDouble();
                        }
                    } catch (JsonPropertyNotFound jsonPropertyNotFound) {
                        log.error("Error getting value for datatype: {} station: {}", datatype.datatypeName(),
                                stationId, jsonPropertyNotFound);

                        resultsBranch.getBranch().put(datatype.datatypeName(),
                                BranchDTO.builder().data(DataDTO.builder().build()).build());
                        continue;
                    }

                    final String unit = getJsonProperty(result, datatype.unitProperty()).asText();
                    final String targetUnit = datatype.targetUnit();

                    Double value = null;
                    try {
                        value = getValue(rawValue, unit, targetUnit);
                    } catch (TransformerNotFound transformerNotFound) {
                        log.error("Error transforming value for datatype: {} station: {}", datatype.datatypeName(),
                                stationId, transformerNotFound);
                        resultsBranch.getBranch().put(datatype.datatypeName(),
                                BranchDTO.builder().data(DataDTO.builder().build()).build());
                        continue;
                    }

                    final JsonNode timestampJson = getJsonProperty(data, metadata.timestampProperty());
                    final Long timestamp = getUnixTimestamp(timestampJson.asText());

                    resultsBranch.getBranch().put(datatype.datatypeName(),
                            BranchDTO.builder().data(DataDTO.builder()
                                            .value(value)
                                            .timestamp(timestamp)
                                            .period(metadata.period())
                                            .build()).build());
                }

                measurements.getBranch().put(stationId, resultsBranch);
            }
            return measurements;
        }
        return null;
    }

    private Long getUnixTimestamp(final String timestamp) {
        ZonedDateTime zdt = ZonedDateTime.parse(timestamp, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return zdt.toEpochSecond();
    }

    private Double getValue(final Double value, final String unit, final String targetUnit) throws TransformerNotFound {
        log.info("Value: {} Unit: {} TargetUnit: {}", value, unit, targetUnit);
        if (Objects.equals(unit, targetUnit)) {
            return value;
        }

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

    private List<StationDTO> getStations(final JsonNode data, final Metadata metadata) throws JsonPropertyNotFound {
        final Metadata.Station stationsMetadata = metadata.stations();
        if (stationsMetadata.type().isArray()) {
            final ArrayNode results = (ArrayNode) getJsonProperty(data, stationsMetadata.property());
            final List<StationDTO> stations = new ArrayList<>();
            for (JsonNode result : results) {
                final String stationId = getJsonProperty(result, stationsMetadata.identification().property()).asText();
                final JsonNode location = getJsonProperty(result, stationsMetadata.location().property());
                Double latitude = null, longitude = null;
                if (stationsMetadata.location().type().isArray()) {
                    longitude = getJsonProperty(location, stationsMetadata.location().longitude()).asDouble();
                    latitude = getJsonProperty(location, stationsMetadata.location().latitude()).asDouble();
                }
                stations.add(StationDTO.builder()
                        .id(stationId)
                        .origin(metadata.origin())
                        .latitude(latitude)
                        .longitude(longitude)
                        .build());
            }
            return stations;
        }
        return null;
    }

    private JsonNode getJsonProperty(final JsonNode json, final String path) throws JsonPropertyNotFound {
        String[] properties = path.split("\\.");
        JsonNode currentNode = json;

        for (String property : properties) {
            log.info("property: {} currentNode: {}", property, currentNode);
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

    private void printJson(final ResultDTO resultDTO) {
        try {
            log.info("Result {}", new ObjectMapper().writeValueAsString(resultDTO));
        } catch (JsonProcessingException e) {
            log.error("Error parsing result to json", e);
        }
    }
}

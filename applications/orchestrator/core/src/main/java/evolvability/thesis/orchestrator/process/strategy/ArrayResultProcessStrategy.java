package evolvability.thesis.orchestrator.process.strategy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import evolvability.thesis.common.metadata.Metadata;
import evolvability.thesis.orchestrator.dtos.result.BranchDTO;
import evolvability.thesis.orchestrator.dtos.result.DataDTO;
import evolvability.thesis.orchestrator.dtos.result.StationDTO;
import evolvability.thesis.orchestrator.exceptions.JsonPropertyNotFound;
import evolvability.thesis.orchestrator.exceptions.ResultTypeNotSupported;
import evolvability.thesis.orchestrator.exceptions.TransformerNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static evolvability.thesis.orchestrator.process.ProcessHelpers.getJsonProperty;
import static evolvability.thesis.orchestrator.process.ProcessHelpers.getValue;
import static evolvability.thesis.orchestrator.process.ProcessHelpers.getUnixTimestamp;

@Component
@Slf4j
public class ArrayResultProcessStrategy implements ProcessStrategy {
    @Override
    public List<StationDTO> getStations(final JsonNode data, final Metadata metadata) throws JsonPropertyNotFound {
        final Metadata.Station stationsMetadata = metadata.stations();
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

    @Override
    public BranchDTO getMeasurements(final JsonNode data, final Metadata metadata) throws JsonPropertyNotFound,
            ResultTypeNotSupported {
        final Metadata.Results resultsMetadata = metadata.results();
        final ArrayNode results = (ArrayNode) getJsonProperty(data, resultsMetadata.property());
        final BranchDTO measurements = new BranchDTO();
        for (JsonNode result : results) {
            final String stationId = getJsonProperty(result, resultsMetadata.idProperty()).asText();
            final BranchDTO resultsBranch = new BranchDTO();
            for (Metadata.Datatype datatype : metadata.datatypes()) {
                Double rawValue = null;
                try {
                    final JsonNode rawValueJson = getJsonProperty(result, datatype.valueProperty());
                    rawValue = rawValueJson.isArray() ? rawValueJson.get(0).asDouble() : rawValueJson.asDouble();
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
}

package evolvability.thesis.orchestrator.process.strategy;

import com.fasterxml.jackson.databind.JsonNode;
import evolvability.thesis.common.metadata.Metadata;
import evolvability.thesis.orchestrator.dtos.result.BranchDTO;
import evolvability.thesis.orchestrator.dtos.result.StationDTO;

import java.util.List;

public interface ProcessStrategy {
    List<StationDTO> getStations(JsonNode data, Metadata metadata) throws Exception;
    BranchDTO getMeasurements(JsonNode data, Metadata metadata) throws Exception;
}

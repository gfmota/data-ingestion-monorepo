package evolvability.thesis.orchestrator.process.strategy;

import com.fasterxml.jackson.databind.JsonNode;
import evolvability.thesis.common.metadata.Metadata;
import evolvability.thesis.orchestrator.dtos.result.BranchDTO;
import evolvability.thesis.orchestrator.dtos.result.StationDTO;
import evolvability.thesis.orchestrator.exceptions.ResultTypeNotSupported;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MapResultProcessStrategy implements ProcessStrategy {
    @Override
    public List<StationDTO> getStations(final JsonNode data, final Metadata metadata) throws Exception {
        throw new Exception("Strategy not implemented yet");
    }

    @Override
    public BranchDTO getMeasurements(final JsonNode data, final Metadata metadata) throws Exception,
            ResultTypeNotSupported {
        throw new Exception("Strategy not implemented yet");
    }
}

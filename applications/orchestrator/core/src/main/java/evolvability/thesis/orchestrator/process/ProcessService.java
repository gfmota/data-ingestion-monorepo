package evolvability.thesis.orchestrator.process;

import com.fasterxml.jackson.databind.JsonNode;
import evolvability.thesis.common.metadata.Metadata;
import evolvability.thesis.orchestrator.dtos.ResultDTO;
import evolvability.thesis.orchestrator.dtos.result.DataTypeDTO;
import evolvability.thesis.orchestrator.process.strategy.ProcessStrategy;
import evolvability.thesis.orchestrator.process.strategy.ProcessStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProcessService {
    public void processMessage(final JsonNode data, final Metadata metadata) throws Exception {
        final ResultDTO result = new ResultDTO();
        final ProcessStrategy processStrategy = ProcessStrategyFactory.getProcessStrategy(metadata.results().type());

        // Extract datatypes
        metadata.datatypes().forEach(dataType -> {
            result.getDataTypes().add(DataTypeDTO.builder()
                    .name(dataType.datatypeName())
                    .unit(dataType.targetUnit())
                    .rtype(dataType.rtype())
                    .build());
        });

        // Extract stations
        result.setStations(processStrategy.getStations(data, metadata));

        // Extract measurements
        result.setMeasurements(processStrategy.getMeasurements(data, metadata));

        log.info("Result {}", result.getJson());
    }
}

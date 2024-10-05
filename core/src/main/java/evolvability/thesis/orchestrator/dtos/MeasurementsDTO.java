package evolvability.thesis.orchestrator.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementsDTO {
    private String name;
    private Map<String, BranchDTO> branch;
}

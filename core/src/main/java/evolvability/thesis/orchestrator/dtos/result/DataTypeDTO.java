package evolvability.thesis.orchestrator.dtos.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataTypeDTO {
    private String name;
    private String rtype;
    private String unit;
    private String description;
}

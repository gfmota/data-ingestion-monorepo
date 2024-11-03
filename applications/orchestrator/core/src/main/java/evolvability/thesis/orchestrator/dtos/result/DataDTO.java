package evolvability.thesis.orchestrator.dtos.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataDTO {
    private Long timestamp;
    private Double value;
    private Integer period;
}

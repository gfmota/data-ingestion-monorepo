package evolvability.thesis.orchestrator.dtos;

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
    private Object value;
    private String period;
}

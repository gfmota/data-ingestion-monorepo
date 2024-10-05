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
public class StationDTO {
    private String id;
    private String name;
    private String origin;
    private Double latitude;
    private Double longitude;
    private Map<String, Object> metadata;
}

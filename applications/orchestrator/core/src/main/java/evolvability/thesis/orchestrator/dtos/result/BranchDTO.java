package evolvability.thesis.orchestrator.dtos.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class BranchDTO {
    private String name;
    private Map<String, BranchDTO> branch;
    private DataDTO data;

    public BranchDTO() {
        this.branch = new HashMap<>();
    }
}

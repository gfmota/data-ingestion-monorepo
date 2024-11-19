package evolvability.thesis.orchestrator.dtos;

import com.google.gson.Gson;
import evolvability.thesis.orchestrator.dtos.result.BranchDTO;
import evolvability.thesis.orchestrator.dtos.result.DataTypeDTO;
import evolvability.thesis.orchestrator.dtos.result.StationDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ResultDTO {
    private List<DataTypeDTO> dataTypes;

    private List<StationDTO> stations;

    private BranchDTO measurements;

    public ResultDTO() {
        this.dataTypes = new ArrayList<>();
        this.stations = new ArrayList<>();
        this.measurements = new BranchDTO();
    }

    private static final Gson GSON = new Gson();

    public String getJson() {
        return GSON.toJson(this);
    }
}

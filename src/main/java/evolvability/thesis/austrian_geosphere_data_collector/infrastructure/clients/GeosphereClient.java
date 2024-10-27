package evolvability.thesis.austrian_geosphere_data_collector.infrastructure.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "geosphere-service", url = "${geosphere-service.url}")
public interface GeosphereClient {
    @GetMapping("/v1/station/current/tawes-v1-10min?parameters=P,FF,DD&output_format=geojson")
    Object getAirPressureCurrentStatus(@RequestParam("station_ids") List<String> stationIds);
}

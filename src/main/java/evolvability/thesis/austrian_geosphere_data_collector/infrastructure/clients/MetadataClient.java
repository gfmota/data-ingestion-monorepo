package evolvability.thesis.austrian_geosphere_data_collector.infrastructure.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "metadata-service", url = "${metadata-service.url}")
public interface MetadataClient {
    @PostMapping("/metadata/{collectorId}")
    void configMetadata(@PathVariable String collectorId, @RequestBody Map<String, Object> metadata);
}

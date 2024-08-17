package evolvability.thesis.data_collector_template.infrastructure.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "metadata-service", url = "${metadata-service.url}")
public interface MetadataClient {
    @PostMapping("/metadata/{collectorId}")
    Map<String, Object> configMetadata(@PathVariable String collectorId, @RequestBody Map<String, Object> metadata);
}

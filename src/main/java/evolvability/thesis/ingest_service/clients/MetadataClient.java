package evolvability.thesis.ingest_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "metadata-service", url = "${metadata-service.url}")
public interface MetadataClient {
    @GetMapping("/metadata/byCollectorId")
    Map<String, Object> getMetadata(@RequestParam String collectorId);
}

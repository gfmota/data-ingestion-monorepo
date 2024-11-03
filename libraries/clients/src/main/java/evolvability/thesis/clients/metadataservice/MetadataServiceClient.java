package evolvability.thesis.clients.metadataservice;

import evolvability.thesis.common.metadata.Metadata;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "metadata-service", url = "http://localhost:8082")
public interface MetadataServiceClient {
    @GetMapping("/metadata/{collectorId}")
    Metadata getMetadata(@PathVariable("collectorId") String collectorId);
}

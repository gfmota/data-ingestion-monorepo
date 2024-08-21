package evolvability.thesis.austrian_geosphere_data_collector.domain.usecases;

import evolvability.thesis.austrian_geosphere_data_collector.infrastructure.clients.MetadataClient;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class MetadataBootConfig implements CommandLineRunner {
    @Value("${metadata.config.path}")
    private String metadataConfigPath;

    @Value("${collector.id}")
    private String collectorId;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MetadataClient metadataClient;

    @Override
    public void run(String... args) throws Exception {
        log.info("Searching for metadata config on {}", metadataConfigPath);
        Map<String, Object> metadata;
        try {
            ClassPathResource resource = new ClassPathResource(metadataConfigPath);
            metadata = objectMapper.readValue(resource.getFile(), Map.class);
        } catch (Exception e) {
            log.error("Error while reading metadata config", e);
            return;
        }

        if (metadata.isEmpty()) {
            log.info("No metadata found");
            return;
        }
        log.info("Metadata found, sending it to config service: {}", metadata);
        try {
            metadataClient.configMetadata(collectorId, metadata);
        } catch (Exception e) {
            log.error("Error while sending metadata to config service", e);
        }
    }
}

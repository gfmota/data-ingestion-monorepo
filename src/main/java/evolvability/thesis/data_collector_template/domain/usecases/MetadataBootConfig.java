package evolvability.thesis.data_collector_template.domain.usecases;

import evolvability.thesis.data_collector_template.infrastructure.clients.MetadataClient;
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
        ClassPathResource resource = new ClassPathResource(metadataConfigPath);
        final Map<String, Object> metadata = objectMapper.readValue(resource.getFile(), Map.class);

        if (metadata.isEmpty()) {
            log.info("No metadata found");
            return;
        }
        log.info("Metadata found, sending it to config service: {}", metadata);
        metadataClient.configMetadata(collectorId, metadata);
    }
}

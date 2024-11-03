package evolvability.thesis.austrian_geosphere_data_collector.domain.usecases;

import evolvability.thesis.austrian_geosphere_data_collector.domain.entity.EnrichedData;
import evolvability.thesis.austrian_geosphere_data_collector.domain.entity.Header;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class DataEnricher {

    @Value("${data.id}")
    private String dataId;

    @Value("${collector.id}")
    private String collectorId;

    public EnrichedData enrichData(Object data) {
        final var header = new Header(LocalDateTime.now(), collectorId, dataId);
        return new EnrichedData(header, Map.of(), data);
    }
}

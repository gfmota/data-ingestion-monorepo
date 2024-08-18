package evolvability.thesis.austrian_geosphere_data_collector.domain.usecases;

import evolvability.thesis.austrian_geosphere_data_collector.domain.entity.EnrichedData;
import evolvability.thesis.austrian_geosphere_data_collector.domain.entity.Header;
import evolvability.thesis.austrian_geosphere_data_collector.domain.gateways.PublisherGateway;
import evolvability.thesis.austrian_geosphere_data_collector.infrastructure.clients.GeosphereClient;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class Collector {
    private static final String EVERY_TEN_MINUTES_CRON = "0 */10 * * * *";

    @Value("${geosphere.stations}")
    private String stations;

    @Value("${spring.application.name}")
    private String collectorName;

    @Autowired
    private PublisherGateway publisherGateway;

    @Autowired
    private GeosphereClient geosphereClient;

    @Scheduled(cron = EVERY_TEN_MINUTES_CRON)
    private void collectData() {
        log.info("Collecting data for stations {}", stations);
        final List<String> stationIds = List.of(stations.split(","));

        try {
            final var rawData = geosphereClient.getAirPressureCurrentStatus(stationIds);
            final var enrichedData = enrichData(rawData);
            publisherGateway.publishData(enrichedData);
        } catch (FeignException e) {
            log.error("Error while collecting data", e);
        }
    }

    private EnrichedData enrichData(Object rawData) {
        final var header = new Header(LocalDateTime.now(), collectorName);
        return new EnrichedData(header, Map.of(), rawData);
    }
}

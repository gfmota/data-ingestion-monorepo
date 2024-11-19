package evolvability.thesis.austrian_geosphere_data_collector.domain.usecases;

import evolvability.thesis.austrian_geosphere_data_collector.domain.gateways.PublisherGateway;
import evolvability.thesis.austrian_geosphere_data_collector.infrastructure.clients.GeosphereClient;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class Collector {
    private static final String EVERY_TEN_MINUTES_CRON = "0 */10 * * * *";

    @Value("${geosphere.stations}")
    private String stations;

    @Autowired
    private PublisherGateway publisherGateway;

    @Autowired
    private GeosphereClient geosphereClient;

    @Autowired
    private DataEnricher dataEnricher;

    @Scheduled(cron = EVERY_TEN_MINUTES_CRON)
    private void collectData() {
        log.info("Collecting data for stations {}", stations);
        final List<String> stationIds = List.of(stations.split(","));

        try {
            final var rawData = geosphereClient.getAirPressureCurrentStatus(stationIds);
            final var enrichedData = dataEnricher.enrichData(rawData);
            publisherGateway.publishData(enrichedData);
        } catch (FeignException e) {
            log.error("Error while collecting data", e);
        }
    }
}

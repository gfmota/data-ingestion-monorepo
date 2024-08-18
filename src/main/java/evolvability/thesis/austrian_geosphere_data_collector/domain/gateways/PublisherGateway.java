package evolvability.thesis.austrian_geosphere_data_collector.domain.gateways;

import evolvability.thesis.austrian_geosphere_data_collector.domain.entity.EnrichedData;

public interface PublisherGateway {
    void publishData(EnrichedData data);
}

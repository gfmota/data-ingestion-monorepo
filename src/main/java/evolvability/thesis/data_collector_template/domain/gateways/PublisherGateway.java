package evolvability.thesis.data_collector_template.domain.gateways;

import evolvability.thesis.data_collector_template.domain.entity.EnrichedData;

public interface PublisherGateway {
    void publishData(EnrichedData data);
}

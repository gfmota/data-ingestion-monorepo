package evolvability.thesis.austrian_geosphere_data_collector.domain.gateways;

import evolvability.thesis.common.dataingestionqueue.DataIngestionMessage;

public interface PublisherGateway {
    void publishData(DataIngestionMessage data);
}

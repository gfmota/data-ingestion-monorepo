package evolvability.thesis.data_collector_template.domain.gateways;

public interface CollectorGateway<T> {
    T collectData();
}

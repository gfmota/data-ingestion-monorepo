package evolvability.thesis.austrian_geosphere_data_collector.domain.usecases;

import evolvability.thesis.common.dataingestionqueue.DataIngestionMessage;
import evolvability.thesis.common.dataingestionqueue.Header;
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

    public DataIngestionMessage enrichData(Object data) {
        final var header = new Header(LocalDateTime.now(), collectorId, dataId);
        return new DataIngestionMessage(header, null, data);
    }
}

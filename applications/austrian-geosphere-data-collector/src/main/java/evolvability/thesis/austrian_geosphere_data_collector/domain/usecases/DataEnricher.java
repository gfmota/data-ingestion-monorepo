package evolvability.thesis.austrian_geosphere_data_collector.domain.usecases;

import evolvability.thesis.common.dataingestionqueue.DataIngestionMessage;
import evolvability.thesis.common.dataingestionqueue.Header;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class DataEnricher {
    @Value("${source.id}")
    private String sourceId;

    public DataIngestionMessage enrichData(Object data) {
        final var header = new Header(LocalDateTime.now(), sourceId);
        return new DataIngestionMessage(header, null, data);
    }
}

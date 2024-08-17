package evolvability.thesis.data_collector_template.domain.entity;

import java.util.Map;

public record EnrichedData(Header header, Map<String, Object> metadata, Object data) {
}

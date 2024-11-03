package evolvability.thesis.common.metadata;

import java.util.List;

public record Metadata(
        String dataId,
        String origin,
        int period,
        String timestampProperty,
        Results results,
        Station stations,
        List<Datatype> datatypes
) {
    public record Results(
            String property,
            Type type,
            String idProperty
    ) {}

    public record Station(
            String property,
            Type type,
            Location location,
            Identification identification
    ) {}

    public record Location(
            String property,
            Type type,
            String longitude,
            String latitude
    ) {}

    public record Identification(
            String property
    ) {}

    public record Datatype(
            String datatypeName,
            String method,
            String unitProperty,
            String valueProperty,
            String targetUnit,
            String rtype
    ) {}
}

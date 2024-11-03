package evolvability.thesis.ingest_service.entities;

import evolvability.thesis.common.metadata.Metadata;

public record DataTransformationMessage(Object rawData, Metadata metadata) {}

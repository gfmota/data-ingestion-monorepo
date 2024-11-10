package evolvability.thesis.ingestcore.entities;

import evolvability.thesis.common.metadata.Metadata;

public record DataTransformationMessage(Object rawData, Metadata metadata) {}

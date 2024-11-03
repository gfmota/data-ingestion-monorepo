package evolvability.thesis.common.dataingestionqueue;

import evolvability.thesis.common.metadata.Metadata;

import java.io.Serializable;

public record DataIngestionMessage(Header header, Metadata metadata, Object data) implements Serializable {
}

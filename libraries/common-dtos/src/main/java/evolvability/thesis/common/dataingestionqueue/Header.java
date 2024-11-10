package evolvability.thesis.common.dataingestionqueue;

import java.io.Serializable;
import java.time.LocalDateTime;

public record Header(LocalDateTime collectedAt, String source) implements Serializable {
}

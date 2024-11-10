package evolvability.thesis.ingestapi.controllers;

import evolvability.thesis.ingestapi.dtos.IngestDataRequestBodyDTO;
import evolvability.thesis.ingestapi.producers.IngestDataPublisher;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingest")
@RequiredArgsConstructor
@Slf4j
public class IngestController {
    private final IngestDataPublisher ingestDataPublisher;

    @PostMapping("/{sourceId}")
    private ResponseEntity<Void> ingestData(
            @PathVariable("sourceId") final String sourceId,
            @Valid @RequestBody final IngestDataRequestBodyDTO requestBody) {
        log.info("Received data to be ingested: {}", requestBody);
        try {
            ingestDataPublisher.publish(sourceId, requestBody.getCollectedAt(),
                    requestBody.getData(), requestBody.getMetadata());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Failed to ingest data: {}", sourceId, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}

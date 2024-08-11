package evolvability.thesis.metadata_service.controllers;

import evolvability.thesis.metadata_service.services.MetadataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/metadata")
@RequiredArgsConstructor
@Slf4j
public class MetadataController {
    private final MetadataService metadataService;

    @GetMapping("/byCollectorId")
    private ResponseEntity<Map<String, Object>> getMetadata(
            @RequestParam("collectorId") final String collectorId) {
        log.info("Getting metadata for collectorId: {}", collectorId);
        return ResponseEntity.ok(Map.of());
    }

    @PostMapping("/{collectorId}")
    private ResponseEntity<String> createMetadata(
            @PathVariable("collectorId") final String collectorId,
            @RequestBody final Map<String, Object> metadata) {
        log.info("Received create metadata for collectorId {} with: {}", collectorId, metadata);
        try {
            return ResponseEntity.ok(metadataService.createMetadata(collectorId, metadata));
        } catch (Exception e) {
            log.error("Error creating metadata for collectorId: {}", collectorId, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}

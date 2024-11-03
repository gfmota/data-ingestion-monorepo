package evolvability.thesis.metadata_service.controllers;

import evolvability.thesis.metadata_service.entities.MetadataDTO;
import evolvability.thesis.metadata_service.services.MetadataService;
import jakarta.validation.Valid;
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

    @GetMapping("/{collectorId}")
    private ResponseEntity<Map<String, Object>> getMetadata(
            @PathVariable("collectorId") final String collectorId) {
        log.info("Getting metadata for collectorId: {}", collectorId);
        return ResponseEntity.ok(metadataService.getMetadata(collectorId));
    }

    @PostMapping("")
    private ResponseEntity<String> createMetadata(@Valid @RequestBody final MetadataDTO metadata) {
        log.info("Received create metadata with: {}", metadata);
        try {
            return ResponseEntity.ok(metadataService.createMetadata(metadata));
        } catch (Exception e) {
            log.error("Error creating metadata: {}", metadata, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}

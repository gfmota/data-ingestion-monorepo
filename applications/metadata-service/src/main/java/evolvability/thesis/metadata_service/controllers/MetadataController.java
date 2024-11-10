package evolvability.thesis.metadata_service.controllers;

import evolvability.thesis.common.metadata.Metadata;
import evolvability.thesis.common.metadata.MetadataDTO;
import evolvability.thesis.metadata_service.exceptions.MetadataNotFoundException;
import evolvability.thesis.metadata_service.services.MetadataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/metadata")
@RequiredArgsConstructor
@Slf4j
public class MetadataController {
    private final MetadataService metadataService;

    @GetMapping("/{sourceId}")
    private ResponseEntity<Metadata> getMetadata(
            @PathVariable("sourceId") final String sourceId) {
        log.info("Getting metadata for sourceId: {}", sourceId);
        try {
            return ResponseEntity.ok(metadataService.getMetadata(sourceId));
        } catch (MetadataNotFoundException e) {
            log.error("Metadata for source id {} not found", sourceId, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error getting metadata for sourceId: {}", sourceId, e);
            return ResponseEntity.internalServerError().build();
        }
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

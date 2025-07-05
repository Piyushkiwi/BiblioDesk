package com.application.bibliodesk.controller;

import com.application.bibliodesk.payload.PublisherDTO;
import com.application.bibliodesk.service.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/publishers")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()") // ✅ Only authenticated users can access
public class PublisherController {

    private final PublisherService publisherService;

    // 🔹 GET all publishers
    @GetMapping("/getAll")
    public ResponseEntity<List<PublisherDTO>> findAllPublishers() {
        List<PublisherDTO> publishers = publisherService.findAllPublishers();
        return ResponseEntity.ok(publishers);
    }

    // 🔹 GET publisher by ID with error handling
    @GetMapping("/getPublisher/{id}")
    public ResponseEntity<?> findPublisherById(@PathVariable Long id) {
        try {
            PublisherDTO publisher = publisherService.findPublisherById(id);
            return ResponseEntity.ok(publisher);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Publisher with ID " + id + " not found.");
        }
    }

    // 🔹 CREATE new publisher with validation
    @PostMapping("/create")
    public ResponseEntity<?> createPublisher(@Valid @RequestBody PublisherDTO publisherDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(getValidationErrors(result));
        }
        PublisherDTO created = publisherService.createPublisher(publisherDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // 🔹 UPDATE publisher by ID with validation and error handling
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePublisher(@PathVariable Long id, @Valid @RequestBody PublisherDTO publisherDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(getValidationErrors(result));
        }

        if (!id.equals(publisherDTO.getId())) {
            return ResponseEntity.badRequest().body("ID in path and body must match.");
        }

        try {
            PublisherDTO updated = publisherService.updatePublisher(id, publisherDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Publisher with ID " + id + " not found.");
        }
    }

    // 🔹 DELETE publisher
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePublisher(@PathVariable Long id) {
        try {
            publisherService.deletePublisher(id);
            return ResponseEntity.ok("Publisher deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Publisher with ID " + id + " not found.");
        }
    }

    // 🔹 Helper: Validation error formatter
    private Map<String, String> getValidationErrors(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
}

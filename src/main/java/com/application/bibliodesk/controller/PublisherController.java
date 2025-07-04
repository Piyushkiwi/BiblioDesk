package com.application.bibliodesk.controller;

import com.application.bibliodesk.payload.PublisherDTO;
import com.application.bibliodesk.service.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;

    @GetMapping
    public ResponseEntity<List<PublisherDTO>> findAllPublishers() {
        List<PublisherDTO> publishers = publisherService.findAllPublishers();
        return ResponseEntity.ok(publishers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherDTO> findPublisherById(@PathVariable Long id) {
        try {
            PublisherDTO publisher = publisherService.findPublisherById(id);
            return ResponseEntity.ok(publisher);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<PublisherDTO> createPublisher(@Valid @RequestBody PublisherDTO publisherDTO) {
        PublisherDTO created = publisherService.createPublisher(publisherDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherDTO> updatePublisher(@PathVariable Long id, @Valid @RequestBody PublisherDTO publisherDTO) {
        if (!id.equals(publisherDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Path ID does not match request body ID.");
        }

        try {
            PublisherDTO updated = publisherService.updatePublisher(id, publisherDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
        try {
            publisherService.deletePublisher(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}

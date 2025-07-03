package com.application.bibliodesk.controller;

import com.application.bibliodesk.entity.Publisher;
import com.application.bibliodesk.service.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;

    @GetMapping
    public ResponseEntity<List<Publisher>> findAllPublishers() {
        List<Publisher> publishers = publisherService.findAllPublishers();
        return ResponseEntity.ok(publishers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> findPublisherById(@PathVariable Long id) {
        Publisher publisher = publisherService.findPublisherById(id);
        return ResponseEntity.ok(publisher);
    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@Valid @RequestBody Publisher publisher) {
        Publisher createdPublisher = publisherService.createPublisher(publisher); // ✅ fixed typo
        return new ResponseEntity<>(createdPublisher, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable Long id, @Valid @RequestBody Publisher publisher) {
        if (!id.equals(publisher.getId())) {
            return ResponseEntity.badRequest().build(); // 400 if ID mismatch
        }
        Publisher updatedPublisher = publisherService.updatePublisher(publisher);
        return ResponseEntity.ok(updatedPublisher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
        publisherService.deletePublisher(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}

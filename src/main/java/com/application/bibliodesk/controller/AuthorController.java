package com.application.bibliodesk.controller;

import com.application.bibliodesk.entity.Author;
import com.application.bibliodesk.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor // Replaces manual @Autowired with constructor injection
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.findAllAuthors();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        Author author = authorService.findAuthorById(id);
        return ResponseEntity.ok(author);
    }

    @PostMapping
    public ResponseEntity<?> createAuthor(@Valid @RequestBody Author author, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Author createdAuthor = authorService.createAuthor(author);
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @Valid @RequestBody Author author, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Validation error: " + bindingResult.getAllErrors());
        }

        // Make sure author.getId() exists – check that the entity has Lombok's @Getter
        if (author.getId() == null || !id.equals(author.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in path does not match ID in request body.");
        }

        authorService.findAuthorById(id); // Ensure exists
        Author updatedAuthor = authorService.updateAuthor(author);
        return ResponseEntity.ok(updatedAuthor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.findAuthorById(id); // Ensure exists
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}

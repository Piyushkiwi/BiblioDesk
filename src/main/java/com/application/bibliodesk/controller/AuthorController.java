package com.application.bibliodesk.controller;

import com.application.bibliodesk.entity.Author;
import com.application.bibliodesk.payload.AuthorDTO;
import com.application.bibliodesk.service.serviceImp.AuthorServiceImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorServiceImp authorService;
    private final ModelMapper modelMapper;

    @GetMapping("/all/getAuthor")
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> dtos = authorService.findAllAuthors().stream()
                .map(author -> {
                    AuthorDTO dto = modelMapper.map(author, AuthorDTO.class);
                    Set<Long> bookIds = author.getBooks().stream().map(b -> b.getId()).collect(Collectors.toSet());
                    dto.setBookIds(bookIds);
                    return dto;
                }).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/get/AuthorBy/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        Author author = authorService.findAuthorById(id);
        AuthorDTO dto = modelMapper.map(author, AuthorDTO.class);
        Set<Long> bookIds = author.getBooks().stream().map(b -> b.getId()).collect(Collectors.toSet());
        dto.setBookIds(bookIds);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/create/Author")
    public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody AuthorDTO requestDTO) {
        Author author = modelMapper.map(requestDTO, Author.class);
        Author saved = authorService.createAuthor(author);
        AuthorDTO responseDTO = modelMapper.map(saved, AuthorDTO.class);
        Set<Long> bookIds = saved.getBooks().stream().map(b -> b.getId()).collect(Collectors.toSet());
        responseDTO.setBookIds(bookIds);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/AuthorBy/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorDTO requestDTO) {
        Author existing = authorService.findAuthorById(id);
        modelMapper.map(requestDTO, existing);
        Author updated = authorService.updateAuthor(existing);
        AuthorDTO responseDTO = modelMapper.map(updated, AuthorDTO.class);
        Set<Long> bookIds = updated.getBooks().stream().map(b -> b.getId()).collect(Collectors.toSet());
        responseDTO.setBookIds(bookIds);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/delete/AuthorBy/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.findAuthorById(id); // Ensure exists
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}

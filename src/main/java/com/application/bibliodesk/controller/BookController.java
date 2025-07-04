package com.application.bibliodesk.controller;

import com.application.bibliodesk.payload.BookDTO;
import com.application.bibliodesk.entity.Book;
import com.application.bibliodesk.service.serviceImp.AuthorServiceImp;
import com.application.bibliodesk.service.serviceImp.BookServiceImp;
import com.application.bibliodesk.service.serviceImp.CategoryServiceImp;
import com.application.bibliodesk.service.serviceImp.PublisherServiceImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookServiceImp bookService;
    private final CategoryServiceImp categoryService;
    private final PublisherServiceImp publisherService;
    private final AuthorServiceImp authorService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.findAllBooks().stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        Book book = bookService.findBookById(id);
        BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
        return ResponseEntity.ok(bookDTO);
    }

    @PostMapping
    public ResponseEntity<?> createBook(@Valid @RequestBody BookDTO bookDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Book book = modelMapper.map(bookDTO, Book.class);
        Book createdBook = bookService.createBook(book);
        BookDTO createdBookDTO = modelMapper.map(createdBook, BookDTO.class);

        return new ResponseEntity<>(createdBookDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        if (!id.equals(bookDTO.getId())) {
            return new ResponseEntity<>("ID in path does not match ID in request body.", HttpStatus.BAD_REQUEST);
        }

        Book book = modelMapper.map(bookDTO, Book.class);
        Book updatedBook = bookService.updateBook(book);
        BookDTO updatedBookDTO = modelMapper.map(updatedBook, BookDTO.class);

        return ResponseEntity.ok(updatedBookDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}

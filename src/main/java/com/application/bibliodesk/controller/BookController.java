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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()") // ✅ All endpoints require authentication
public class BookController {

    private final BookServiceImp bookService;
    private final CategoryServiceImp categoryService;
    private final PublisherServiceImp publisherService;
    private final AuthorServiceImp authorService;
    private final ModelMapper modelMapper;

    // ✅ Get all books
    @GetMapping("/getAll")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.findAllBooks().stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    // ✅ Get book by ID (with not-found handling)
    @GetMapping("/getBookBy/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        try {
            Book book = bookService.findBookById(id);
            BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
            return ResponseEntity.ok(bookDTO);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Book with ID " + id + " not found.");
        }
    }

    // ✅ Create new book (uses BookDTO as request and response)
    @PostMapping("/createBook")
    public ResponseEntity<?> createBook(@Valid @RequestBody BookDTO bookDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(getValidationErrors(result));
        }

        Book book = modelMapper.map(bookDTO, Book.class);
        Book saved = bookService.createBook(book);
        BookDTO responseDTO = modelMapper.map(saved, BookDTO.class);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // ✅ Update book (uses BookDTO as request and response)
    @PutMapping("/updateBook/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(getValidationErrors(result));
        }

        if (!id.equals(bookDTO.getId())) {
            return ResponseEntity.badRequest().body("ID in path and ID in body must match.");
        }

        try {
            Book existingBook = bookService.findBookById(id); // Ensure book exists
            modelMapper.map(bookDTO, existingBook); // Map updated fields
            Book updated = bookService.updateBook(existingBook);
            BookDTO responseDTO = modelMapper.map(updated, BookDTO.class);
            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Book with ID " + id + " not found.");
        }
    }

    // ✅ Delete book (with existence check)
    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        try {
            bookService.findBookById(id); // Ensure book exists
            bookService.deleteBook(id);
            return ResponseEntity.ok("Book deleted successfully.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Book with ID " + id + " not found.");
        }
    }

    // ✅ Helper for validation errors
    private Map<String, String> getValidationErrors(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
}

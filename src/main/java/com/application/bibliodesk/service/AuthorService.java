package com.application.bibliodesk.service;

import com.application.bibliodesk.entity.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAllAuthors();
    Author findAuthorById(Long id);
    Author createAuthor(Author author);
    Author updateAuthor(Author author);
    void deleteAuthor(Long id);
}

package com.application.bibliodesk.service.serviceImp;

import com.application.bibliodesk.entity.Author;
import com.application.bibliodesk.repository.AuthorRepository;
import com.application.bibliodesk.service.AuthorService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImp implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<Author> findAllAuthors() {
        return authorRepository.findAllWithBooks(); // Eager fetch books
    }

    @Override
    public Author findAuthorById(Long id) {
        return authorRepository.findByIdWithBooks(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    @Override
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    @Override
    public Author updateAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        authorRepository.deleteById(author.getId());
    }
}

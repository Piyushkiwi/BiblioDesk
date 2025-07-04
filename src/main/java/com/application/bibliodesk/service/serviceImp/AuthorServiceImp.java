package com.application.bibliodesk.service.serviceImp;

import com.application.bibliodesk.entity.Author;
import com.application.bibliodesk.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImp implements com.application.bibliodesk.service.AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author findAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        return author;
    }

    @Override
    public Author createAuthor(Author author){
        Author savedAuthor=authorRepository.save(author);
        return savedAuthor;
    }

    @Override
    public Author updateAuthor(Author author){
        Author updatedAuthor=authorRepository.save(author);
        return updatedAuthor;
    }

    @Override
    public void deleteAuthor(Long id){
        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
        authorRepository.deleteById(author.getId());
    }

}

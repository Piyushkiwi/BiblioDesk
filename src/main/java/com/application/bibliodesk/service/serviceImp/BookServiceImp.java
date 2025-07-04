package com.application.bibliodesk.service.serviceImp;

import com.application.bibliodesk.entity.Book;
import com.application.bibliodesk.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImp implements com.application.bibliodesk.service.BookService {

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book with ID " + id + " not found"));
    }

    @Transactional
    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public Book updateBook(Book book) {
        if (!bookRepository.existsById(book.getId())) {
            throw new IllegalArgumentException("Book with ID " + book.getId() + " not found for update.");
        }
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new IllegalArgumentException("Book with ID " + id + " not found for deletion.");
        }
        bookRepository.deleteById(id);
    }
}

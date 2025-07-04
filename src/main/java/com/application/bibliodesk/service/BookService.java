package com.application.bibliodesk.service;

import com.application.bibliodesk.entity.Book;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookService {
    @Transactional(readOnly = true)
    List<Book> findAllBooks();

    @Transactional(readOnly = true)
    Book findBookById(Long id);

    @Transactional
    Book createBook(Book book);

    @Transactional
    Book updateBook(Book book);

    @Transactional
    void deleteBook(Long id);
}

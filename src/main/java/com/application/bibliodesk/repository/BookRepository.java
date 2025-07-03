package com.application.bibliodesk.repository;

import com.application.bibliodesk.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    // Add custom query methods here if needed
}

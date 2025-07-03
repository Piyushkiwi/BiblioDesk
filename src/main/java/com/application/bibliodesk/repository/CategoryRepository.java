package com.application.bibliodesk.repository;

import com.application.bibliodesk.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Optional: Category findByName(String name);
}

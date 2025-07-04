package com.application.bibliodesk.service;

import com.application.bibliodesk.entity.Category;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryService {
    @Transactional(readOnly = true)
    List<Category> findAllCategories();

    @Transactional(readOnly = true)
    Category findCategoryById(Long id);

    @Transactional
    Category createCategory(Category category);

    @Transactional
    Category updateCategory(Category category);

    @Transactional
    void deleteCategory(Long id);
}

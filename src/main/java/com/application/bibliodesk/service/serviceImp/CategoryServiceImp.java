package com.application.bibliodesk.service.serviceImp;

import com.application.bibliodesk.entity.Category;
import com.application.bibliodesk.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements com.application.bibliodesk.service.CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category with ID " + id + " not found"));
    }

    @Transactional
    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public Category updateCategory(Category category) {
        if (!categoryRepository.existsById(category.getId())) {
            throw new IllegalArgumentException("Category with ID " + category.getId() + " not found for update.");
        }
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Category with ID " + id + " not found for deletion.");
        }
        categoryRepository.deleteById(id);
    }
}

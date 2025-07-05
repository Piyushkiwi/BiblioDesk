package com.application.bibliodesk.controller;

import com.application.bibliodesk.payload.CategoryDTO;
import com.application.bibliodesk.entity.Category;
import com.application.bibliodesk.service.serviceImp.CategoryServiceImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()") // ✅ Require authentication for all endpoints
public class CategoryController {

    private final CategoryServiceImp categoryService;
    private final ModelMapper modelMapper;

    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryDTO>> findAllCategories() {
        List<CategoryDTO> categories = categoryService.findAllCategories().stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/getCategory/{id}")
    public ResponseEntity<?> findCategoryById(@PathVariable Long id) {
        try {
            Category category = categoryService.findCategoryById(id);
            return ResponseEntity.ok(modelMapper.map(category, CategoryDTO.class));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Category with ID " + id + " not found.");
        }
    }

    @PostMapping("/createCategory")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Category category = modelMapper.map(categoryDTO, Category.class);
        Category createdCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(modelMapper.map(createdCategory, CategoryDTO.class), HttpStatus.CREATED);
    }

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            Category existing = categoryService.findCategoryById(id); // ensures category exists
            categoryDTO.setId(id); // ✅ forcefully set path ID into DTO
            modelMapper.map(categoryDTO, existing);
            Category updatedCategory = categoryService.updateCategory(existing);
            return ResponseEntity.ok(modelMapper.map(updatedCategory, CategoryDTO.class));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Category with ID " + id + " not found.");
        }
    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.findCategoryById(id); // ensure exists
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("Category deleted successfully.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Category with ID " + id + " not found.");
        }
    }
}

package com.application.bibliodesk.controller;

import com.application.bibliodesk.payload.CategoryDTO;
import com.application.bibliodesk.entity.Category;
import com.application.bibliodesk.service.serviceImp.CategoryServiceImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServiceImp categoryService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAllCategories() {
        List<CategoryDTO> categories = categoryService.findAllCategories().stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findCategoryById(@PathVariable Long id) {
        Category category = categoryService.findCategoryById(id);
        return ResponseEntity.ok(modelMapper.map(category, CategoryDTO.class));
    }

    @PostMapping
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        if (!id.equals(categoryDTO.getId())) {
            return new ResponseEntity<>("ID in path does not match ID in request body.", HttpStatus.BAD_REQUEST);
        }

        Category category = modelMapper.map(categoryDTO, Category.class);
        Category updatedCategory = categoryService.updateCategory(category);
        return ResponseEntity.ok(modelMapper.map(updatedCategory, CategoryDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}

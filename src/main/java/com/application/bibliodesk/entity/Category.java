package com.application.bibliodesk.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Category name is required") // Validation: Name cannot be blank
    @Size(min = 2, max = 50, message = "Category name must be between 2 and 50 characters")
    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;


    @ManyToMany(mappedBy = "categories", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Book> books = new HashSet<>(); // Initialize the Set to prevent NullPointerExceptions

    // Your original constructor, kept for compatibility if needed.
    public Category(String name) {
        this.name = name;
    }
}
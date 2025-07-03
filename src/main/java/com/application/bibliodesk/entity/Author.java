package com.application.bibliodesk.entity;


import lombok.Data;
import lombok.NoArgsConstructor;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank; // For checking if a string is not null and not empty
import jakarta.validation.constraints.Size;   // For checking string length constraints

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Author name cannot be empty") // Ensures the name is not null or empty
    @Size(min = 2, max = 100, message = "Author name must be between 2 and 100 characters") // Specific size
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;


    @NotBlank(message = "Author description cannot be empty") // Ensures the description is not null or empty
    @Size(min = 10, max = 250, message = "Author description must be between 10 and 250 characters") // Specific size
    @Column(name = "description", length = 250, nullable = false)
    private String description;


    @ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL)
    private Set<Book> books = new HashSet<>();

    public Author(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

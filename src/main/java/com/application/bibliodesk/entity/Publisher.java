package com.application.bibliodesk.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "publishers")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Publisher name is required")
    @Size(min = 3, max = 100, message = "Publisher name must be between 3 and 100 characters")
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    // ✅ FIX: Import correct Book entity from same package
    @ManyToMany(mappedBy = "publishers", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Book> books = new HashSet<>();

    public Publisher(String name) {
        this.name = name;
    }
}

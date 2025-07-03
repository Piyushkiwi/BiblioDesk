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
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "ISBN is required")
    @Size(min = 10, max = 17, message = "ISBN must be between 10 and 17 characters")
    @Column(name = "isbn", length = 17, nullable = false, unique = true)
    private String isbn;

    @NotBlank(message = "Book name is required")
    @Size(min = 2, max = 250, message = "Book name must be between 2 and 250 characters")
    @Column(name = "name", length = 250, nullable = false)
    private String name;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    @Column(name = "description", length = 1000, nullable = false)
    private String description;

    // --- Many-to-Many with Authors ---
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new HashSet<>();

    // --- Many-to-Many with Categories ---
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "books_categories",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    // --- Many-to-Many with Publishers ---
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "books_publishers",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "publisher_id"))
    private Set<Publisher> publishers = new HashSet<>();

    // Constructor for creating a book without ID or associations
    public Book(String isbn, String name, String description) {
        this.isbn = isbn;
        this.name = name;
        this.description = description;
    }

    // --- Helper Methods ---

    public void addAuthor(Author author) {
        this.authors.add(author);
        author.getBooks().add(this);
    }

    public void removeAuthor(Author author) {
        this.authors.remove(author);
        author.getBooks().remove(this);
    }

    public void addCategory(Category category) {
        this.categories.add(category);
        category.getBooks().add(this);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
        category.getBooks().remove(this);
    }

    public void addPublisher(Publisher publisher) {
        this.publishers.add(publisher);
        publisher.getBooks().add(this);
    }

    public void removePublisher(Publisher publisher) {
        this.publishers.remove(publisher);
        publisher.getBooks().remove(this);
    }
}

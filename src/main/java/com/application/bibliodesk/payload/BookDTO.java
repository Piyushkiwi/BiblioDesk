package com.application.bibliodesk.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {

    private Long id;

    @NotBlank(message = "ISBN is required")
    @Size(min = 10, max = 17, message = "ISBN must be between 10 and 17 characters")
    private String isbn;

    @NotBlank(message = "Book name is required")
    @Size(min = 2, max = 250, message = "Book name must be between 2 and 250 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    private String description;

    // IDs of related entities (optional: can also include their names if needed)
    private Set<Long> authorIds;
    private Set<Long> categoryIds;
    private Set<Long> publisherIds;
}

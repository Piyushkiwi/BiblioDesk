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
public class AuthorDTO {

    private Long id;

    @NotBlank(message = "Author name cannot be empty")
    @Size(min = 2, max = 100, message = "Author name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Author description cannot be empty")
    @Size(min = 10, max = 250, message = "Author description must be between 10 and 250 characters")
    private String description;

    // Optional: use to display book IDs or titles associated with the author (if needed)
    private Set<Long> bookIds;
}

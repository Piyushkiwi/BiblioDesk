package com.application.bibliodesk.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "Author name cannot be empty")
    @Size(min = 2, max = 100, message = "Author name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Author description cannot be empty")
    @Size(min = 10, max = 250, message = "Author description must be between 10 and 250 characters")
    private String description;
}
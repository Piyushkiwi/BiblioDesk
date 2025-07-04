package com.application.bibliodesk.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublisherDTO {

    private Long id;

    @NotBlank(message = "Publisher name is required")
    @Size(min = 3, max = 100, message = "Publisher name must be between 3 and 100 characters")
    private String name;
}

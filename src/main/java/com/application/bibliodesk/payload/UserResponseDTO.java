package com.application.bibliodesk.payload;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO{
    private Long id;
    private String name;
    private String description;
}

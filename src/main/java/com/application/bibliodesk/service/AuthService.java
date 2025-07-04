package com.application.bibliodesk.service;

import com.application.bibliodesk.payload.RegisterUserDTO;
import com.application.bibliodesk.payload.requestDTO.UserRequestDTO;
import com.application.bibliodesk.payload.responseDTO.UserResponseDTO;

public interface AuthService {
    UserResponseDTO register(RegisterUserDTO request);
    UserResponseDTO authenticate(UserRequestDTO request);
}

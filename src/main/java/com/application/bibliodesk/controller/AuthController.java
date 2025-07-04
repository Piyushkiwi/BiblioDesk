package com.application.bibliodesk.controller;

import com.application.bibliodesk.payload.requestDTO.UserRequestDTO;
import com.application.bibliodesk.payload.responseDTO.UserResponseDTO;
import com.application.bibliodesk.payload.RegisterUserDTO;
import com.application.bibliodesk.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController{

    private final AuthService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody RegisterUserDTO request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserResponseDTO> authenticate(@RequestBody UserRequestDTO request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
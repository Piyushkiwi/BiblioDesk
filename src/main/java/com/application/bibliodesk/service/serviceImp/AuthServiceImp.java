package com.application.bibliodesk.service.serviceImp;

import com.application.bibliodesk.payload.requestDTO.UserRequestDTO;
import com.application.bibliodesk.payload.responseDTO.UserResponseDTO;
import com.application.bibliodesk.payload.RegisterUserDTO;
import com.application.bibliodesk.entity.User;
import com.application.bibliodesk.repository.UserRepository;
import com.application.bibliodesk.security.jwt.JwtUtil;
import com.application.bibliodesk.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtUtil jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;

    @Override
    public UserResponseDTO register(RegisterUserDTO request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        // Assuming a single role "LIBRARIAN" for all users created this way
        return new UserResponseDTO(jwtToken, user.getUsername(), "LIBRARIAN");
    }

    @Override
    public UserResponseDTO authenticate(UserRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found after authentication"));

        String jwtToken = jwtService.generateToken(user);
        // Assuming a single role "LIBRARIAN" for all authenticated users
        return new UserResponseDTO(jwtToken, user.getUsername(), "LIBRARIAN");
    }
}
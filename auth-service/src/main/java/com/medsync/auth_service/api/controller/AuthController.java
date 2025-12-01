package com.medsync.auth_service.api.controller;

import com.medsync.auth_service.api.dto.AuthResponse;
import com.medsync.auth_service.api.dto.LoginRequest;
import com.medsync.auth_service.api.dto.RegisterRequest;
import com.medsync.auth_service.application.auth.AuthResult;
import com.medsync.auth_service.application.auth.AuthService;
import com.medsync.auth_service.application.auth.LoginCommand;
import com.medsync.auth_service.application.auth.RegisterCommand;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        RegisterCommand command = new RegisterCommand(
                request.getEmail(),
                request.getPassword(),
                request.getFullName(),
                request.getRole()
        );
        AuthResult result = authService.register(command);
        return toAuthResponse(result);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        LoginCommand command = new LoginCommand(
                request.getEmail(),
                request.getPassword()
        );
        AuthResult result = authService.login(command);
        return toAuthResponse(result);
    }

    private AuthResponse toAuthResponse(AuthResult result) {
        Set<String> roles = result.roles().stream()
                .map(Enum::name)
                .collect(Collectors.toSet());

        return new AuthResponse(
                result.accessToken(),
                "Bearer",
                result.userId(),
                result.email(),
                result.fullName(),
                roles
        );
    }
}


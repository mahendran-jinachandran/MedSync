package com.medsync.auth_service.api.controller;


import com.medsync.auth_service.api.dto.UserResponse;
import com.medsync.auth_service.application.user.UserService;
import com.medsync.auth_service.domain.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/me")
    public UserResponse getCurrentUser(Authentication authentication) {
        Long userId = (Long) authentication.getDetails();
        User user = userService.getById(userId);

        Set<String> roles = user.getRoles().stream()
                .map(Enum::name)
                .collect(Collectors.toSet());

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getStatus().name(),
                roles
        );
    }
}


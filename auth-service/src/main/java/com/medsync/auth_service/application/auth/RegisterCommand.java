package com.medsync.auth_service.application.auth;

import com.medsync.auth_service.domain.role.RoleName;

public record RegisterCommand(
        String email,
        String rawPassword,
        String fullName,
        String role
) {
}

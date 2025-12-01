package com.medsync.auth_service.application.auth;

import com.medsync.auth_service.domain.role.RoleName;
import lombok.Data;

import java.util.Set;

public record AuthResult(
        String accessToken,
        Long userId,
        String email,
        String fullName,
        Set<RoleName> roles
) {
}

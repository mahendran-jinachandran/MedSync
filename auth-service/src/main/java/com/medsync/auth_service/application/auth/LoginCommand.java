package com.medsync.auth_service.application.auth;

import lombok.Data;

public record LoginCommand(
        String email,
        String rawPassword
) {
}

package com.medsync.auth_service.api.dto;

import com.medsync.auth_service.domain.role.RoleName;
import com.medsync.auth_service.domain.user.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String fullName;

    private String status;
    private Set<String> roles;
}
